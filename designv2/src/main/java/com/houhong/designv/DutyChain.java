package com.houhong.designv;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @program: algorithm-work
 * @description: 责任链定义
 * @author: houhong
 * @create: 2022-08-26 00:34
 **/

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DutyChain {

    /**
    *  实现类
    **/
    public Class<?>  implClass();
    /**
    *  顺序
    **/
    public  int order() default 1;

}
