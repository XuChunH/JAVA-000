package com.yehui.netty.filter;

import com.yehui.netty.core.filter.HttpRequestFilter;
import com.yehui.netty.core.filter.HttpRequestFilterChain;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.UUID;

/**
 * @author yehui
 * @date 2020/11/1
 */
public class TraceFilter implements HttpRequestFilter {

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx, HttpRequestFilterChain filterChain) {

        final String uuid = UUID.randomUUID()
            .toString();
        fullRequest.headers().
            add("trace_id", uuid);
        filterChain.filter(fullRequest, ctx);
    }
}
