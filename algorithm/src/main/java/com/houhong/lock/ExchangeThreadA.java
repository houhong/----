package com.houhong.lock;

import java.util.concurrent.Exchanger;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-09-15 21:53
 **/
public class ExchangeThreadA extends Thread {

    private Exchanger<String> exchanger;

    public  ExchangeThreadA(Exchanger<String> exchanger){

        this.exchanger = exchanger;
    }
    @Override
    public void  run(){

        try {
            System.out.println("线程A从线程B中获取值:"+ exchanger.exchange("中国人B"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}