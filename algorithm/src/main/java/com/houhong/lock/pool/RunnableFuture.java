package com.houhong.lock.pool;


public interface RunnableFuture<V> extends Future<V>,Runnable {

    void run();
}
