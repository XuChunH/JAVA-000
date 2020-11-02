/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yehui.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpVersion;

import java.net.URI;
import java.util.Map;

public class NettyClientRequest {

    private final Bootstrap bootstrap;

    private final URI uri;

    private final ByteBufOutputStream body;

    public NettyClientRequest(Bootstrap bootstrap, URI uri) {

        this.bootstrap = bootstrap;
        this.uri = uri;
        this.body = new ByteBufOutputStream(Unpooled.buffer(1024));
    }

    protected void execute(final HttpHeaders headers, NettyClientCallback callback) {

        ChannelFutureListener connectionListener = future -> {
            if (future.isSuccess()) {
                Channel channel = future.channel();
                channel.pipeline()
                    .addLast(new RequestExecuteHandler(callback));
                FullHttpRequest nettyRequest = createFullHttpRequest(headers);
                channel.writeAndFlush(nettyRequest);
            } else {
                callback.onFail(null, null);
            }
        };

        this.bootstrap.connect(this.uri.getHost(), getPort(this.uri))
            .addListener(connectionListener);
    }

    private FullHttpRequest createFullHttpRequest(HttpHeaders headers) {
        // 先至支持 get
        io.netty.handler.codec.http.HttpMethod nettyMethod = io.netty.handler.codec.http.HttpMethod.valueOf("GET");

        String authority = this.uri.getRawAuthority();
        String path = this.uri.toString()
            .substring(this.uri.toString()
                .indexOf(authority) + authority.length());
        FullHttpRequest nettyRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, nettyMethod, path, this.body.buffer());

        for (Map.Entry<String, String> header : headers) {
            nettyRequest.headers()
                .add(header.getKey(), header.getValue());
        }
        if (!nettyRequest.headers()
            .contains(HttpHeaderNames.CONTENT_LENGTH) && this.body.buffer()
            .readableBytes() > 0) {
            nettyRequest.headers()
                .set(HttpHeaderNames.CONTENT_LENGTH, this.body.buffer()
                    .readableBytes());
        }

        return nettyRequest;
    }

    private static int getPort(URI uri) {

        int port = uri.getPort();
        if (port == -1) {
            if ("http".equalsIgnoreCase(uri.getScheme())) {
                port = 80;
            } else if ("https".equalsIgnoreCase(uri.getScheme())) {
                port = 443;
            }
        }
        return port;
    }

    /**
     * A SimpleChannelInboundHandler to update the given SettableListenableFuture.
     */
    private static class RequestExecuteHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

        private final NettyClientCallback callback;

        public RequestExecuteHandler(NettyClientCallback callback) {

            this.callback = callback;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext context, FullHttpResponse response) throws Exception {

            callback.onSuccess(context, new NettyClientResponse(context, response));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws Exception {

            callback.onFail(context, cause);
        }
    }

}
