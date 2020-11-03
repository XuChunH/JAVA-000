package com.yehui.netty.filter;

import com.yehui.netty.core.inbound.HttpInboundServer;
import com.yehui.netty.httpclient.HttpClientOutboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServerFilterApplication {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerFilterApplication.class);
    
    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "1.0.0";
    
    public static void main(String[] args) {
        String proxyServer = System.getProperty("proxyServer","http://localhost:8088");
        String proxyPort = System.getProperty("proxyPort","8888");
        
          //  http://localhost:8888/api/hello  ==> gateway API
          //  http://localhost:8088/api/hello  ==> backend service
    
        int port = Integer.parseInt(proxyPort);
        logger.info("{} {} starting...", GATEWAY_NAME, GATEWAY_VERSION);
        final HttpClientOutboundHandler httpClientOutboundHandler = new HttpClientOutboundHandler(proxyServer);
        final TraceFilter traceFilter = new TraceFilter();
        final ForbiddenFilter forbiddenFilter = new ForbiddenFilter();
        HttpInboundServer server = new HttpInboundServer(port, httpClientOutboundHandler, traceFilter, forbiddenFilter);
        logger.info("{} {} started at http://localhost:{} for server:{}", GATEWAY_NAME, GATEWAY_VERSION, port, proxyServer);
        try {
            server.run();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
