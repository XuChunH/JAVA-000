package com.yehui.springbean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath:spring-service.xml")
@SpringBootApplication
public class SpringBeanApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringBeanApplication.class, args);
    }


    @Bean
    public BeanAnnotationBean beanAnnotationBean() {
        return new BeanAnnotationBean();
    }
}
