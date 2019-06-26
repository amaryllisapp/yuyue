package com.ps.lc.net.subscriber;

import android.util.Log;

import com.ps.lc.net.NetworkCode;
import com.ps.lc.net.NetworkManager;
import com.ps.lc.net.exception.ApiException;
import com.ps.lc.net.exception.HttpException;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;

/**
 * Created by zhangwulin on 2017/1/10.
 * Email:zhangwulin@feitaikeji.com
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> implements ISubscriber {
    @Override
    public void destroy() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }


    @Override
    public void onCompleted() {
        completed();
        NetworkManager.getInstance().removeSubscribar(this);
    }

    @Override
    public void onError(Throwable e) {
        Log.getStackTraceString(e);
        try {
            handleError(e);
        } catch (Exception er) {
            showError(NetworkCode.RESPONSE_E30000, er.getMessage());
            Log.getStackTraceString(er);
        }
        if (e instanceof HttpException) {
            showError(NetworkCode.RESPONSE_E50000, NetworkCode.getCodeDesc(NetworkCode.RESPONSE_E50000));
        } else if (e instanceof SocketTimeoutException) {
            showError(NetworkCode.RESPONSE_E21000, NetworkCode.getCodeDesc(NetworkCode.RESPONSE_E21000));
        } else if (e instanceof ConnectException) {
            showError(NetworkCode.RESPONSE_E22000, NetworkCode.getCodeDesc(NetworkCode.RESPONSE_E22000));
        } else if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            showErrorWithData(apiException.getCode(), apiException.getMessage(), apiException.getData());
        } else if (e instanceof NoRouteToHostException) {
            showError(NetworkCode.RESPONSE_E51000, NetworkCode.getCodeDesc(NetworkCode.RESPONSE_E51000));
        } else if (e instanceof UnknownHostException) {
            showError(NetworkCode.RESPONSE_E52000, NetworkCode.getCodeDesc(NetworkCode.RESPONSE_E52000));
        } else {
            showError(NetworkCode.RESPONSE_E30000, e.getMessage());
        }
        completed();
        NetworkManager.getInstance().removeSubscribar(this);
    }

    @Override
    public void onNext(T t) {
        handleNext(t);
        completed();
        NetworkManager.getInstance().removeSubscribar(this);
    }

    protected abstract void handleError(Throwable e);

    protected abstract void showError(String code, String msg);

    protected abstract void showErrorWithData(String code, String msg, Object object);

    protected abstract void handleNext(T t);

    protected abstract void completed();
}
