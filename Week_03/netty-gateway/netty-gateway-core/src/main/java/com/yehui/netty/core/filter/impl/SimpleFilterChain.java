package com.yehui.netty.core.filter.impl;

import com.yehui.netty.core.filter.HttpRequestFilter;
import com.yehui.netty.core.filter.HttpRequestFilterChain;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;
import java.util.Objects;

/**
 * 非线程安全
 *
 * @author yehui
 * @date 2020/11/1
 */
public class SimpleFilterChain implements HttpRequestFilterChain {

    private int index;

    private List<HttpRequestFilter> filterList;

    public SimpleFilterChain(List<HttpRequestFilter> filterList) {

        if (Objects.isNull(filterList) || filterList.isEmpty()) {
            throw new IllegalArgumentException("filterList is empty");
        }
        this.filterList = filterList;
    }

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        if (index >= filterList.size()) {
            return;
        }
        final HttpRequestFilter httpRequestFilter = filterList.get(index);
        index++;
        httpRequestFilter.filter(fullRequest, ctx, this);
    }
}
