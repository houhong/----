package com.houhong.springResource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-08-22 01:06
 **/
@Configuration
@ComponentScan(basePackages = "com.houhong.springResource")
public class MouduleConfig2 {

    @Bean
    public String module2(){

        return "我是模块2";
    }
}