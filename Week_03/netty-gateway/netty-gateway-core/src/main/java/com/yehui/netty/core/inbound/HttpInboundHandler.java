package com.yehui.netty.core.inbound;

import com.yehui.netty.core.filter.HttpRequestFilter;
import com.yehui.netty.core.filter.HttpRequestFilterChain;
import com.yehui.netty.core.filter.impl.SimpleFilterChain;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;

import java.util.List;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private final List<HttpRequestFilter> filterList;

    public HttpInboundHandler(List<HttpRequestFilter> filterList) {

        this.filterList = filterList;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {

        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        try {
            FullHttpRequest fullRequest = (FullHttpRequest)msg;
            final SimpleFilterChain filterChain = new SimpleFilterChain(filterList);
            filterChain.filter(fullRequest, ctx);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

}
