package com.yehui.netty.client;

import com.yehui.netty.core.inbound.HttpInboundServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyClientServerApplication {

    private static final Logger logger = LoggerFactory.getLogger(NettyClientServerApplication.class);
    
    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "1.0.0";
    
    public static void main(String[] args) {
//        String proxyServer = System.getProperty("proxyServer","http://localhost:8088");
        String proxyServer = System.getProperty("proxyServer","http://192.168.16.213:10024/");
        String proxyPort = System.getProperty("proxyPort","8888");
        
          //  http://localhost:8888/api/hello  ==> gateway API
          //  http://localhost:8088/api/hello  ==> backend service
    
        int port = Integer.parseInt(proxyPort);
        logger.info("{} {} starting...", GATEWAY_NAME, GATEWAY_VERSION);
        final NettyHttpClient nettyHttpClient = new NettyHttpClient();
        final NettyHttpClientOutboundHandler httpClientOutboundHandler = new NettyHttpClientOutboundHandler(proxyServer, nettyHttpClient);
        HttpInboundServer server = new HttpInboundServer(port, httpClientOutboundHandler);
        logger.info("{} {} started at http://localhost:{} for server:{}", GATEWAY_NAME, GATEWAY_VERSION, port, proxyServer);
        try {
            server.run();
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            nettyHttpClient.close();
        }
    }
}
