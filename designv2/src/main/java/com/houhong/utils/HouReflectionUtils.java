package com.houhong.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @program: algorithm-work
 * @description: 反射测试工具类
 * @author: houhong
 * @create: 2022-08-27 10:07
 **/
public class HouReflectionUtils {

    public static  <T> T  doWithFields(T t,CallBackTest callback) throws Exception {

        Method[] methods = t.getClass().getDeclaredMethods();

        for (Method method : methods) {

            method.invoke(t);
            callback.handler(method);
        }
        return  t;
    }
}