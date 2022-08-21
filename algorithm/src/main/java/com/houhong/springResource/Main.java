package com.houhong.springResource;

import org.springframework.context.annotation.DependsOn;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-08-21 14:02
 **/
public class Main {


    public static void main(String[] args) {

        new ClassPathXmlApplicationContext();

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.getBean("");

    }
}