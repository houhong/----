package com.houhong.lock.test;

import com.houhong.lock.CyclicBarrier;
import lombok.SneakyThrows;

import java.util.concurrent.BrokenBarrierException;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-09-15 21:31
 **/
public class CyliBariierThread extends Thread {

    private CyclicBarrier barrier;


    public CyliBariierThread(CyclicBarrier cyclicBarrier) {
        this.barrier = cyclicBarrier;
    }


    @Override
    public void run(){

        System.out.println(Thread.currentThread().getName() +" 凑足两个才能继续进行");
        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() +" 已经凑足两个继续进行");
    }

}