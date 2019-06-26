package com.ps.lc.net.subscriber;


import android.text.TextUtils;
import android.util.Log;

import com.ps.lc.net.cache.Cache;
import com.ps.lc.net.cache.DiskLruCacheHelper;
import com.ps.lc.net.callback.HttpDataCallback;
import com.ps.lc.net.service.BaseResponse;
import com.ps.lc.net.service.HttpService;

import java.io.IOException;

import javax.annotation.Nonnull;

/**
 * Created by zhangwulin on 2016/11/2.
 * Eamil zhangwulin@feitaikeji.com
 */

public class HttpDataSubscriber<T, R extends BaseResponse<T>> extends BaseSubscriber<R> {
    private HttpDataCallback<T> mCallBack;
    private DiskLruCacheHelper mDiskLruCacheHelper;
    private HttpService mService;

    public HttpDataSubscriber(@Nonnull HttpService service, @Nonnull DiskLruCacheHelper diskLruCacheHelper, HttpDataCallback<T> callback) {
        this.mCallBack = callback;
        mService = service;
        mDiskLruCacheHelper = diskLruCacheHelper;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (null != mCallBack) {
            mCallBack.onHttpStart();
        }
        if (isUseCache()) {
            openCache();
            int cacheStatus = mService.getCache();
            Cache<R> t = mDiskLruCacheHelper.get(mService.getCacheKey(), HttpService.CACHE_NEVER_EXPIRE == cacheStatus ? -1 : mService.getCacheTimeout(),
                    mService.getTType(), HttpService.CACHE_EXPIRE_DELETE == cacheStatus);
            if (null != t && null != t.getData() && null != t.getData().getTData()) {
                mCallBack.onHttpCache(t.getData().getTData());
                if (!(HttpService.CACHE_EXPIRE_REQUEST == cacheStatus && t.isExpire())) {
                    unsubscribe();
                    completed();
                }
            }
        }

    }


    @Override
    protected void handleError(Throwable e) {
        if (isUseCache()) {
            mDiskLruCacheHelper.remove(mService.getCacheKey());
        }

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
        T t = r.getTData();
        if (null != mCallBack) {
            mCallBack.onHttpSuccess(t);
        }
        if (isUseCache()) {
            mDiskLruCacheHelper.put(mService.getCacheKey(), r);
        }
    }

    @Override
    protected void completed() {
        if (null != mCallBack) {
            mCallBack.onHttpCompleted();
        }
        closeCache();
    }

    protected boolean openCache() {
        try {
            mDiskLruCacheHelper.open();
            return true;
        } catch (IOException e) {
            Log.getStackTraceString(e);
            return false;
        }
    }

    protected boolean isUseCache() {
        return mService.isCache() && (!TextUtils.isEmpty(mService.getCacheKey()) || !TextUtils.isEmpty(mService.getMethodGetGenerateKey()));
    }


    protected boolean closeCache() {
        try {
            mDiskLruCacheHelper.flush();
        } catch (IOException e) {
            Log.getStackTraceString(e);
        }
        try {
            mDiskLruCacheHelper.close();
            return true;
        } catch (IOException e) {
            Log.getStackTraceString(e);
            return false;
        }
    }


}
