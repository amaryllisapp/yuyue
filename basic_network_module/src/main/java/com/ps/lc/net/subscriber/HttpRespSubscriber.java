/*
 * Copyright (C) 2012-2017 feitaikeji Corporation. All rights reserved.
 */

package com.ps.lc.net.subscriber;

import com.ps.lc.net.callback.HttpRespCallback;
import com.ps.lc.net.service.BaseResponse;

/**
 * Created by zhangwulin on 2017/1/17.
 * Email:zhangwulin@feitaikeji.com
 * 约定：不返回data只返回resp,用于判断，没缓存
 */

public class HttpRespSubscriber<R extends BaseResponse> extends BaseSubscriber<R> {
    private HttpRespCallback<R> mCallBack;

    public HttpRespSubscriber(HttpRespCallback<R> callback) {
        this.mCallBack = callback;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (null != mCallBack) {
            mCallBack.onHttpStart();
        }

    }

    @Override
    protected void handleError(Throwable e) {

    }

    @Override
    protected void showError(String code, String msg) {
        if (null != mCallBack) {
            mCallBack.onHttpFailure(msg, code);
        }
    }

    @Override
    protected void showErrorWithData(String code, String msg, Object object) {
        if (null != mCallBack) {
            mCallBack.onHttpFailureWithData(msg, code, object);
        }
    }

    @Override
    protected void handleNext(R r) {
        if (null != mCallBack) {
            mCallBack.onHttpSuccess(r);
        }
    }

    @Override
    protected void completed() {
        if (null != mCallBack) {
            mCallBack.onHttpCompleted();
        }
    }


}