package com.houhong.designv;

import org.springframework.stereotype.Component;

/**
 * @program: algorithm-work
 * @description: 第一个链
 * @author: houhong
 * @create: 2022-08-26 00:43
 **/
@Component
@DutyChain(implClass = SecondHandler.class,order = 2)
public class SecondHandler extends AbstractHandler {

    @Override
    public String handler() {

        return "我是第二个链";
    }
}