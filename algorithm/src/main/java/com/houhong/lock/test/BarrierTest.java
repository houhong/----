package com.houhong.lock.test;

import com.houhong.lock.CyclicBarrier;
import lombok.SneakyThrows;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-09-15 21:33
 **/
public class BarrierTest {


    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                System.out.println("全部来了");
            }
        });

        for (int i = 0; i < 4; i++) {
            CyliBariierThread cyliBariierThread = new CyliBariierThread(cyclicBarrier);
            cyliBariierThread.start();
            try {
                Thread.sleep(1000 * 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}