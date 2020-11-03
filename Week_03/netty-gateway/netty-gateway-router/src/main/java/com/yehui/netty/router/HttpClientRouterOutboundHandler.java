package com.yehui.netty.router;

import com.yehui.netty.core.inbound.OutboundHandler;
import com.yehui.netty.core.router.HttpEndpointRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpUtil;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author yehui
 * @date 2020/11/1
 */
public class HttpClientRouterOutboundHandler implements OutboundHandler {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientRouterOutboundHandler.class);

    private CloseableHttpAsyncClient httpclient;

    private ExecutorService proxyService;

    private HttpEndpointRouter router;

    public HttpClientRouterOutboundHandler(HttpEndpointRouter router) {

        this.router = router;

        int cores = Runtime.getRuntime()
            .availableProcessors() * 2;
        long keepAliveTime = 1000;
        int queueSize = 2048;
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        proxyService = new ThreadPoolExecutor(cores, cores, keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
            new NamedThreadFactory("proxyService"), handler);

        IOReactorConfig ioConfig = IOReactorConfig.custom()
            .setConnectTimeout(1000)
            .setSoTimeout(1000)
            .setIoThreadCount(cores)
            .setRcvBufSize(32 * 1024)
            .build();

        httpclient = HttpAsyncClients.custom()
            .setMaxConnTotal(40)
            .setMaxConnPerRoute(8)
            .setDefaultIOReactorConfig(ioConfig)
            .setKeepAliveStrategy((response, context) -> 6000)
            .build();
        httpclient.start();
    }

    /**
     * 处理请求的逻辑
     *
     * @param fullRequest
     * @param ctx
     */
    @Override
    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {

        // 先确定要调用服务的虚拟地址
        String vip = "vip";
        // 真实地址
        final String realAddress = router.route(vip);
        logger.info("realAddress: {}", realAddress);
        final String url = realAddress + fullRequest.uri();
        proxyService.submit(() -> fetchGet(fullRequest, ctx, url));
    }

    private void fetchGet(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url) {

        final HttpGet httpGet = new HttpGet(url);
        //httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
        httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
        httpclient.execute(httpGet, new FutureCallback<HttpResponse>() {

            @Override
            public void completed(final HttpResponse endpointResponse) {

                try {
                    handleResponse(inbound, ctx, endpointResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }

            @Override
            public void failed(final Exception ex) {

                httpGet.abort();
                ex.printStackTrace();
            }

            @Override
            public void cancelled() {

                httpGet.abort();
            }
        });
    }

    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final HttpResponse endpointResponse)
        throws Exception {

        FullHttpResponse response = null;
        try {
            byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());

            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));

            final Header[] allHeaders = endpointResponse.getAllHeaders();
            for (Header allHeader : allHeaders) {
                response.headers().set(allHeader.getName(), allHeader.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (fullRequest != null) {
                final HttpHeaders requestHeaders = fullRequest.headers();
                if (requestHeaders.contains("trace_id")) {
                    response.headers().add("trace_id", requestHeaders.get("trace_id"));
                }
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response)
                        .addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        cause.printStackTrace();
        ctx.close();
    }
}
