package com.ps.lc.net.subscriber;

import com.ps.lc.net.NetworkManager;
import com.ps.lc.net.callback.UploadCallback;
import com.ps.lc.net.service.BaseResponse;

/**
 * Created by zhangwulin on 2017/1/10.
 * Email:zhangwulin@feitaikeji.com
 */

public class UploadSubscriber<R extends BaseResponse> extends BaseSubscriber<R> {
    private UploadCallback<R> mCallBack;

    public UploadSubscriber(UploadCallback<R> callback) {
        mCallBack = callback;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (null != mCallBack) {
            mCallBack.onUploadStart();
        }
    }


    @Override
    public void onNext(R baseResp) {
        // 服务器连接成功
        if (null != mCallBack) {
            mCallBack.onUploadSuccess(baseResp);
        }
        completed();
        NetworkManager.getInstance().removeSubscribar(this);
    }

    @Override
    protected void handleError(Throwable e) {

    }

    @Override
    protected void showError(String code, String msg) {
        if (null != mCallBack) {
            mCallBack.onUploadFailure(msg, code);
        }
    }

    @Override
    protected void showErrorWithData(String code, String msg, Object object) {
        // unused
    }

    @Override
    protected void handleNext(R r) {

    }

    @Override
    protected void completed() {
        if (null != mCallBack) {
            mCallBack.onUploadCompleted();
        }
    }

}
