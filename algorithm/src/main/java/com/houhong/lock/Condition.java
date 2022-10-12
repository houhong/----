package com.houhong.lock;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-09-15 00:37
 **/

import java.util.concurrent.TimeUnit;
import java.util.Date;


public interface Condition {


    void await() throws InterruptedException;


    void awaitUninterruptibly();


    long awaitNanos(long nanosTimeout) throws InterruptedException;


    boolean await(long time, TimeUnit unit) throws InterruptedException;


    boolean awaitUntil(Date deadline) throws InterruptedException;


    void signal();

    void signalAll();
}
