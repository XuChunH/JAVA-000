package com.yehui.netty.core.filter.impl;

import com.yehui.netty.core.filter.HttpRequestFilter;
import com.yehui.netty.core.filter.HttpRequestFilterChain;
import com.yehui.netty.core.inbound.OutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author yehui
 * @date 2020/11/1
 */
public class OutboundFilter implements HttpRequestFilter {

    private OutboundHandler handler;

    public OutboundFilter(OutboundHandler handler) {

        this.handler = handler;
    }

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx, HttpRequestFilterChain filterChain) {
        handler.handle(fullRequest, ctx);
    }
}
