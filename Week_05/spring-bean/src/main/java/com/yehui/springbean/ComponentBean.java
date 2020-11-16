package com.yehui.springbean;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author yehui
 * @date 2020/11/16
 */
@Component
public class ComponentBean {

    @PostConstruct
    public void postConstruct() {
        System.out.println("I am ComponentBean, i am init by Spring");
    }

}
