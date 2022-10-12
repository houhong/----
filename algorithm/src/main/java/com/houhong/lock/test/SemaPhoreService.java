package com.houhong.lock.test;

import java.util.concurrent.Semaphore;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-09-15 21:41
 **/
public class SemaPhoreService {

    private Semaphore semaphore = new Semaphore(2);

    public void testmethod() {

        try {
            semaphore.acquire();

            System.out.println(Thread.currentThread().getName() + "begin timeer" + System.currentTimeMillis());
            Thread.sleep(1000 * 10);
            System.out.println(Thread.currentThread().getName() + "end timeer" + System.currentTimeMillis());
            semaphore.release();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}