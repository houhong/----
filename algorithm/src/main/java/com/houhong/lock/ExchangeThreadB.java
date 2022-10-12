package com.houhong.lock;

import java.util.concurrent.Exchanger;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-09-15 21:54
 **/
public class ExchangeThreadB extends Thread {

    private Exchanger<String> exchanger;

    public  ExchangeThreadB(Exchanger<String> exchanger){

        this.exchanger = exchanger;
    }
    @Override
    public void  run(){

        try {
            System.out.println("线程B从线程A中获取值:"+ exchanger.exchange("中国人A"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}