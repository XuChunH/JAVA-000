package com.yehui.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.URI;
import java.util.concurrent.TimeUnit;

public class NettyHttpClient {

    private int connectTimeout = -1;

    private int readTimeout = -1;

    private int maxResponseSize;

    private final Bootstrap bootstrap;

    private final EventLoopGroup eventLoopGroup;

    public NettyHttpClient() {
        this(-1, -1, 1024 * 1024 * 10);
    }

    public NettyHttpClient(int connectTimeout, int readTimeout, int maxResponseSize) {

        this.maxResponseSize = maxResponseSize;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        int ioWorkerCount = Runtime.getRuntime().availableProcessors() * 2;
        this.eventLoopGroup = new NioEventLoopGroup(ioWorkerCount);
        bootstrap = new Bootstrap();
        bootstrap.group(this.eventLoopGroup).channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {

                    final SocketChannelConfig config = channel.config();
                    if (connectTimeout > 0) {
                        config.setConnectTimeoutMillis(connectTimeout);
                    }
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(new HttpClientCodec());
                    pipeline.addLast(new HttpObjectAggregator(maxResponseSize));
                    if (readTimeout > 0) {
                        pipeline.addLast(new ReadTimeoutHandler(readTimeout,
                            TimeUnit.MILLISECONDS));
                    }
                }
            });
    }

    public void close() {
        eventLoopGroup.shutdownGracefully();
    }

    public NettyClientRequest createRequest(URI uri) {
        return new NettyClientRequest(bootstrap, uri);
    }

//    public void connect(String host, int port) throws Exception {
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//
//        try {
//            Bootstrap b = new Bootstrap();
//            b.group(workerGroup);
//            b.channel(NioSocketChannel.class);
//            b.option(ChannelOption.SO_KEEPALIVE, true);
//            b.handler(new ChannelInitializer<SocketChannel>() {
//                @Override
//                public void initChannel(SocketChannel ch) throws Exception {
//                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
//                    ch.pipeline().addLast(new HttpResponseDecoder());
//                    // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
//                    ch.pipeline().addLast(new HttpRequestEncoder());
//                }
//            });
//
//            // Start the client.
//            ChannelFuture f = b.connect(host, port).sync();
//
//            f.channel().write(request);
//            f.channel().flush();
//            f.channel().closeFuture().sync();
//        } finally {
//            workerGroup.shutdownGracefully();
//        }
//
//    }
}