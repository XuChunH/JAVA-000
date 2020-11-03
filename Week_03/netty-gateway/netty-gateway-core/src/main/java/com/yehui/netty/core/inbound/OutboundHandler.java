package com.yehui.netty.core.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 调用真实服务的逻辑
 *
 * @author yehui
 * @date 2020/11/1
 */
public interface OutboundHandler {

    /**
     * 处理请求的逻辑
     *
     * @param fullRequest
     * @param ctx
     */
    void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx);

}
