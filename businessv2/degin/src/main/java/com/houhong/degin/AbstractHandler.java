package com.houhong.degin;

/**
 * @program: algorithm-work
 * @description: 责任链定义
 * @author: houhong
 * @create: 2022-08-26 00:34
 **/
public abstract class AbstractHandler<T> {

    /**
     * 下一关用当前抽象类来接收
     */
    private  AbstractHandler next;

    /**
    *  处理
    **/
    public abstract  T handler();



    public AbstractHandler getNext() {
        return next;
    }

    public void setNext(AbstractHandler next) {
        this.next = next;
    }
}