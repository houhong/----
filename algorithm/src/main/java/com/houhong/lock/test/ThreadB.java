package com.houhong.lock.test;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-09-15 21:44
 **/
public class ThreadB extends Thread{

    private SemaPhoreService service;

    public ThreadB(SemaPhoreService service){
        this.service = service;
    }

    @Override
    public  void run(){
        service.testmethod();
    }
}
