package com.yehui.netty.router;

import com.yehui.netty.core.router.HttpEndpointRouter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author yehui
 * @date 2020/11/2
 */
public class SimpleRouter implements HttpEndpointRouter {

    private final Random random;

    public SimpleRouter() {

        this.random = new Random();
    }

    @Override
    public String route(String vip) {

        final List<String> serverList = getServerList(vip);
        final int i = random.nextInt(serverList.size());

        return serverList.get(i);
    }

    /**
     * 通过虚拟地址获取真实的服务列表
     *
     * @param vip
     * @return
     */
    private List<String> getServerList(String vip) {

        final List<String> serverList = new ArrayList<>();
        serverList.add("http://localhost:8088");
        serverList.add("http://localhost:8081");

        return serverList;
    }
}
