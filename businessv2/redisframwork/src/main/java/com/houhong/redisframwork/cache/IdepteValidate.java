package com.houhong.redisframwork.cache;


import java.lang.annotation.*;

/**
 * @author houhong
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IdepteValidate {

    /**
     * 默认30S内不能重复提交  -1 表示只能开启一次
     **/
    long period() default 30 * 1000;

    /**
     * 是否打开 默认打开
     **/
    boolean isOpen() default true;

    /**
     * 操作人 默认是system
     **/
    String operCode() default "system";
}
