package com.houhong.orderservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @program: algorithm-work
 * @description: 串行化定时执行。超时增加
 * @author: houhong
 * @create: 2022-09-11 16:35
 **/
public class TimedSupervisorTask extends TimerTask {
    private static final Logger logger = LoggerFactory.getLogger(TimedSupervisorTask.class);

    private ScheduledExecutorService scheduledExecutorService;
    private ThreadPoolExecutor threadPoolExecutor;
    private Long timeOutMills;
    private Runnable task;

    private AtomicLong delay;
    private Long maxDelay;

    public TimedSupervisorTask(ScheduledExecutorService scheduledExecutorService, ThreadPoolExecutor threadPoolExecutor,
                               Long timeOutMills, Runnable runnable) {
        this.scheduledExecutorService = scheduledExecutorService;
        this.threadPoolExecutor = threadPoolExecutor;
        this.timeOutMills = timeOutMills;
        this.task = runnable;
    }

    @Override
    public void run() {

        Future future = null;
        try {
            future = threadPoolExecutor.submit(task);
            //主线程同步等待结果
            future.get(timeOutMills, TimeUnit.MICROSECONDS);
            //正常
            delay.set(timeOutMills);

        } catch (TimeoutException e) {
            logger.error("task supervisor timed out", e);
            long currentDelay = delay.get();
            long newDelay = Math.min( maxDelay, currentDelay * 2);
            delay.compareAndSet(currentDelay,newDelay);

        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if(future != null){
                    future.cancel(true);
            }
            scheduledExecutorService.schedule(this,
                    delay.get(),TimeUnit.MICROSECONDS);
        }

    }
}