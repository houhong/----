package com.houhong.springResource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-08-22 01:06
 **/
@Configuration
public class MouduleConfig {

    @Bean
    public String module1(){

        return "我是模块1";
    }
}