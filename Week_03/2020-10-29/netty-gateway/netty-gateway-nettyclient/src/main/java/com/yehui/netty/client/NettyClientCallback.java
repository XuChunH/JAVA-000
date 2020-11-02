package com.yehui.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author yehui
 * @date 2020/11/1
 */
public interface NettyClientCallback {

    void onSuccess(ChannelHandlerContext context, NettyClientResponse response);

    void onFail(ChannelHandlerContext context, Throwable e);

}
