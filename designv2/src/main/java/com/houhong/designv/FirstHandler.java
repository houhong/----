package com.houhong.designv;

import org.springframework.aop.config.AopConfigUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Component;

/**
 * @program: algorithm-work
 * @description: 第一个链
 * @author: houhong
 * @create: 2022-08-26 00:43
 **/
@Component
@DutyChain(implClass = FirstHandler.class, order = 1)
public class FirstHandler extends AbstractHandler {

    @Override
    public String handler() {

        //获取
        ((FirstHandler) AopContext.currentProxy())
                .handler();
        return "我是第一个链条";
    }

}