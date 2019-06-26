package com.ps.lc.net.exception;


/**
 * Created by zhangwulin on 2017/1/3.
 * Email:zhangwulin@feitaikeji.com
 * api异常
 */

public class ApiException extends RuntimeException {
    /**
     * 用String，若是int转下就是
     */
    private final String code;
    private Object data;

    public ApiException(String code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public ApiException(String code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 异常码
     */
    public String getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

}
