/*
 * Copyright (C) 2012-2017 feitaikeji Corporation. All rights reserved.
 */

package com.ps.lc.net.service;

import android.net.ConnectivityManager;

import com.ps.lc.net.NetworkManager;
import com.ps.lc.net.callback.NetWorkCallback;
import com.ps.lc.net.callback.UploadCallback;

import rx.Observable;

/**
 * Created by zhangwulin on 2017/1/10.
 * Email:zhangwulin@feitaikeji.com
 */

public abstract class UploadService<S, R extends BaseResponse> extends BaseService<S, R> {

    /**
     * 上传
     *
     * @param callback
     */
    public void upload(UploadCallback<R> callback) {
        execute(callback);
    }

    /**
     * 连网
     *
     * @param callBack
     */
    @Override
    public void execute(NetWorkCallback callBack) {
        if (callBack instanceof UploadCallback) {
            mSubscriber = NetworkManager.getInstance().upload(this, (UploadCallback) callBack);
        } else {
            throw new IllegalArgumentException("callback type is error");
        }
    }

    /**
     * 取消连网
     */
    @Override
    public void cancel() {
        if (null != mSubscriber) {
            mSubscriber.destroy();
            mSubscriber = null;
        }
    }


    /**
     * 设置service
     *
     * @param service
     * @return
     */
    @Override
    public abstract Observable<R> getObservable(S service);
//----------------------示例
//    /*上传，没用断点*/
//    @POST("/file")
//    @Multipart
//    Observable<ResponseBody> uploadFile(@Part("file") RequestBody file, @Part("description") RequestBody description);
}
