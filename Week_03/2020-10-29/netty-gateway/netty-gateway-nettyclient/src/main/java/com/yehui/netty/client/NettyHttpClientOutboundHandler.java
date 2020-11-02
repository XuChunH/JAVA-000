package com.yehui.netty.client;

import com.yehui.netty.core.inbound.OutboundHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

import java.net.URI;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author yehui
 * @date 2020/11/1
 */
public class NettyHttpClientOutboundHandler implements OutboundHandler {

    private final String backendUrl;

    private final NettyHttpClient nettyHttpClient;

    public NettyHttpClientOutboundHandler(String backendUrl, NettyHttpClient nettyHttpClient) {

        this.backendUrl = backendUrl.endsWith("/") ? backendUrl.substring(0, backendUrl.length() - 1) : backendUrl;
        this.nettyHttpClient = nettyHttpClient;
    }

    /**
     * 处理请求的逻辑
     *
     * @param fullRequest
     * @param ctx
     */
    @Override
    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {

        final String url = this.backendUrl + fullRequest.uri();
        final URI uri = URI.create(url);
        final NettyClientRequest request = nettyHttpClient.createRequest(uri);
        request.execute(fullRequest.headers(), new NettyClientCallback() {

            @Override
            public void onSuccess(ChannelHandlerContext context, NettyClientResponse response) {

                final FullHttpResponse nettyResponse = response.getNettyResponse();

                ctx.write(nettyResponse);
                ctx.flush();
            }

            @Override
            public void onFail(ChannelHandlerContext context, Throwable e) {
                exceptionCaught(context, e);
            }
        });
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        cause.printStackTrace();
        ctx.close();
    }
}
