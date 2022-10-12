package com.houhong.lock.test;

import com.houhong.lock.CountDownLatch;

import java.util.concurrent.TimeUnit;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-09-15 21:26
 **/
public class SecondThread  extends Thread {

    private CountDownLatch countDownLatch;


    public SecondThread(CountDownLatch latch) {
        super();
        this.countDownLatch = latch;
    }

    @Override
    public void run() {
        System.out.println("执行run .... ");
        try {
            countDownLatch.await(1, TimeUnit.SECONDS);
            System.out.println("继续执行");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}