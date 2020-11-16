package com.yehui.springbean;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author yehui
 * @date 2020/11/16
 */
public class XmlBean implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("I am XmlBean, i am init by Spring");
    }
}
