package com.yehui.netty.client;

import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author yehui
 * @date 2020/11/1
 */
public class NettyClientResponse {

    private final ChannelHandlerContext context;

    private final FullHttpResponse nettyResponse;

    private final ByteBufInputStream body;

    public NettyClientResponse(ChannelHandlerContext context, FullHttpResponse nettyResponse) {

        this.context = context;
        this.nettyResponse = nettyResponse;
        this.body = new ByteBufInputStream(this.nettyResponse.content());
        this.nettyResponse.retain();
    }

    public FullHttpResponse getNettyResponse() {

        return nettyResponse;
    }

    public ByteBufInputStream getBody() throws IOException {
        return this.body;
    }

    public void close() {
        this.nettyResponse.release();
        this.context.close();
    }


}
