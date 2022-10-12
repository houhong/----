package com.houhong.designv;

/**
 * @program: algorithm-work
 * @description: 责任链定义
 * @author: houhong
 * @create: 2022-08-26 00:34
 **/
public abstract class AbstractHandler {

    /**
     * 下一关用当前抽象类来接收
     */
    private  AbstractHandler next;

    /**
    *  处理
    **/
    public abstract  String handler();



    public AbstractHandler getNext() {
        return next;
    }

    public void setNext(AbstractHandler next) {
        this.next = next;
    }
}