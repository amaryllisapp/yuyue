package com.ps.lc.net.cache;

/**
 * Created by zhangwulin on 2017/12/4.
 * Email:zhangwulin@feitaikeji.com
 */

public class Cache<T> {
    private T mData;
    private boolean mExpire;

    public Cache() {
    }

    public Cache(T data, boolean expire) {
        mData = data;
        mExpire = expire;
    }

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

    public boolean isExpire() {
        return mExpire;
    }

    public void setExpire(boolean expire) {
        mExpire = expire;
    }
}
