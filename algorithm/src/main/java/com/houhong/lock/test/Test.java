package com.houhong.lock.test;

import com.houhong.lock.CountDownLatch;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-09-15 21:20
 **/
public class Test {

    public static void main(String[] args) throws Exception {

        CountDownLatch latch = new CountDownLatch(1);
        FirsThread firsThread = new FirsThread(latch);
        SecondThread secondThread = new SecondThread(latch);

        firsThread.start();
        secondThread.start();

        Thread.sleep(1000* 5);
        latch.countDown();
    }
}