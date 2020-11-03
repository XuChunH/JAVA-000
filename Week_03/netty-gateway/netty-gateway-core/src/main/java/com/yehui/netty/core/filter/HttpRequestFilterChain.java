package com.yehui.netty.core.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author yehui
 * @date 2020/11/1
 */
public interface HttpRequestFilterChain {

    void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx);

}
