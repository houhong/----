package com.houhong.thirdpay.exception;

/**
 * @program: algorithm-work
 * @description: 退款异常
 * @author: houhong
 * @create: 2022-10-06 16:53
 **/
public class RefundException extends java.lang.RuntimeException {

    public RefundException() {
    }

    public RefundException(String message) {
        super(message);
    }

    public RefundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefundException(Throwable cause) {
        super(cause);
    }

    public RefundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}