package com.ps.lc.net.exception;

/**
 * Created by zhangwulin on 2017/1/4.
 * Email:zhangwulin@feitaikeji.com
 * https验证异常
 */

public class HttpException extends ApiException {

    public HttpException(String code, String message) {
        super(code, message);
    }

}
