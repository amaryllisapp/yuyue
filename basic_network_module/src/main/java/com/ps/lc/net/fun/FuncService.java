/*
 * Copyright (C) 2012-2017 feitaikeji Corporation. All rights reserved.
 */

package com.ps.lc.net.fun;

import com.ps.lc.net.NetworkCode;
import com.ps.lc.net.exception.ApiException;
import com.ps.lc.net.service.BaseResponse;

import rx.functions.Func1;

/**
 * Created by zhangwulin on 2017/1/16.
 * Email:zhangwulin@feitaikeji.com
 */

public class FuncService<R extends BaseResponse> implements Func1<R, R> {


    @Override
    public R call(R r) {
        if (null == r) {
            throw NetworkCode.getHttpException(NetworkCode.RESPONSE_E20000);
        } else if (r.isSuccess()) {
//            return r.getTData();//不返回data，返回resp要考虑没有data的情况
            return r;
        } else {
            if (r.isBaseApiSuccess()) {
                throw new ApiException(r.getTCode(), r.getTCodeMsg(), r.getTData());
            } else {
                throw new ApiException(r.getBaseApiCode(), r.getBaseApiCodeDesc(), r.getTData());
            }
        }
    }

}