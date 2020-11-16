package com.yehui.springbean;

import javax.annotation.PostConstruct;

/**
 * @author yehui
 * @date 2020/11/16
 */
public class BeanAnnotationBean {

    @PostConstruct
    public void postConstruct() {
        System.out.println("I am BeanAnnotationBean, i am init by Spring");
    }

}
