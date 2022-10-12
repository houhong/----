package com.houhong.lock.test;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-09-15 21:43
 **/
public class ThreadA extends Thread{

    private SemaPhoreService service;

    public ThreadA(SemaPhoreService service){
        this.service = service;
    }

    @Override
    public  void run(){
        service.testmethod();
    }
}