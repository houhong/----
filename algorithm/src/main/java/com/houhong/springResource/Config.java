package com.houhong.springResource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * @program: algorithm-work
 * @description: 配置类
 * @author: houhong
 * @create: 2022-08-22 00:14
 **/
/*@ComponentScan(basePackageClasses = {
        Config.class
},includeFilters ={
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = MyBean.class)
},useDefaultFilters = false)*/
@Configuration
public class Config {

    @Bean(value = "car")
    public  Car car(){
        return  new Car();
    }

    @Bean(value = "scanClass1")
    public  ScanClass1 scaneClass1(){
        return  new ScanClass1();
    }


}