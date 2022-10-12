package com.houhong.thirdpay.exception;

/**
 * @program: algorithm-work
 * @description: 转账业务异常
 * @author: houhong
 * @create: 2022-10-06 19:39
 **/
public class TransferException extends RuntimeException {

    public TransferException() {
    }

    public TransferException(String message) {
        super(message);
    }

    public TransferException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransferException(Throwable cause) {
        super(cause);
    }

    public TransferException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
