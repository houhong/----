package com.houhong.lock.pool;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-09-19 23:18
 **/
public class ExecutionException extends Exception {

    private static final long serialVersionUID = 7830266012832686185L;

    protected ExecutionException() { }


    protected ExecutionException(String message) {
        super(message);
    }


    public ExecutionException(String message, Throwable cause) {
        super(message, cause);
    }


    public ExecutionException(Throwable cause) {
        super(cause);
    }
}