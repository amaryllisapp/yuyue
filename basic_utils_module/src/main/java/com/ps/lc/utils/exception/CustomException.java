package com.ps.lc.utils.exception;


/**
 *自定义exception
 */
public class CustomException extends RuntimeException {
    public CustomException() {
    }

    public CustomException(String detailMessage) {
        super(detailMessage);
    }

    public CustomException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public CustomException(Throwable throwable) {
        super(throwable);
    }
}
