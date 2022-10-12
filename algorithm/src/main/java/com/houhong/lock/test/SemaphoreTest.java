package com.houhong.lock.test;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-09-15 21:39
 **/
public class SemaphoreTest {

    public static void main(String[] args) {

        SemaPhoreService semaPhoreService = new SemaPhoreService();

        ThreadA threadA = new ThreadA(semaPhoreService);
        ThreadB threadB = new ThreadB(semaPhoreService);
        ThreadC threadC = new ThreadC(semaPhoreService);

        threadA.start();
        threadB.start();
        threadC.start();
    }
}