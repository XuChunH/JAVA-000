package com.yehui.netty.filter;

import com.yehui.netty.core.filter.HttpRequestFilter;
import com.yehui.netty.core.filter.HttpRequestFilterChain;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpUtil;
import org.apache.http.Header;
import org.apache.http.util.EntityUtils;

import java.util.UUID;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 模拟限制请求场景
 * @author yehui
 * @date 2020/11/1
 */
public class ForbiddenFilter implements HttpRequestFilter {

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx, HttpRequestFilterChain filterChain) {

        final String uri = fullRequest.uri();
        if (uri.contains("forbidden")) {
            String message = "forbidden access";
            final byte[] data = message.getBytes();
            final  FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(data));
            final HttpHeaders responseHeaders = response.headers();

            responseHeaders.add("Content-Type", "text/plain;charset=UTF-8");
            responseHeaders.add("Content-Length", data.length);

            final HttpHeaders headers = fullRequest.headers();
            if (headers.contains("trace_id")) {
                responseHeaders.add("trace_id", headers.get("trace_id"));
            }
            if (!HttpUtil.isKeepAlive(fullRequest)) {
                ctx.write(response)
                    .addListener(ChannelFutureListener.CLOSE);
            } else {
                ctx.write(response);
            }
            ctx.flush();
            return;
        }
        filterChain.filter(fullRequest, ctx);
    }
}
