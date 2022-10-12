package com.houhong.degin;

/**
 * @program: algorithm-work
 * @description: 第一个链
 * @author: houhong
 * @create: 2022-08-26 00:43
 **/
@DutyChain(implClass = SecondHandler.class,order = 2)
public class SecondHandler extends AbstractHandler<String> {

    @Override
    public String handler() {

        return "我是第二个链";
    }
}