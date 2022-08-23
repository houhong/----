package com.houhong.springResource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-08-22 00:12
 **/
public class Test2 {


    public static void main(String[] args) {


        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MouduleConfig2.class);


        String[] definitionNames = context.getBeanDefinitionNames();

        for (String definitionName : definitionNames) {
            System.out.println(definitionName);
        }

    }


}