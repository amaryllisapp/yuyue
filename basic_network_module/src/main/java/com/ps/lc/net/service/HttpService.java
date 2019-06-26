package com.ps.lc.net.service;

import android.text.TextUtils;

import com.ps.lc.net.NetworkManager;
import com.ps.lc.net.Utils;
import com.ps.lc.net.callback.HttpDataCallback;
import com.ps.lc.net.callback.HttpRespCallback;
import com.ps.lc.net.callback.NetWorkCallback;

import java.lang.reflect.Type;

import rx.Observable;

/**
 * Created by zhangwulin on 2016/11/2.
 * Eamil zhangwulin@feitaikeji.com
 */

public abstract class HttpService<S, R extends BaseResponse> extends BaseService<S, R> {
    /**
     * 不使用缓存，默认
     */
    public static final int CACHE_NONE = 0;
    /**
     * 过期不显示缓存直接连网
     */
    public static final int CACHE_EXPIRE_DELETE = 1;
    /**
     * 过期先显示缓存再连网
     */
    public static final int CACHE_EXPIRE_REQUEST = 2;
    /**
     * 缓存永不过期
     */
    public static final int CACHE_NEVER_EXPIRE = 3;
    /**
     * 重试次数
     */
    public static final int DEFAULT_RETRY_COUNT = 2;
    /**
     * 重试延迟时间 毫秒
     */
    public static final int DEFAULT_RETRY_DELAY = 3000;
    /**
     * 重试叠加延迟 毫秒
     */
    public static final int DEFAULT_RETRY_INCREASE_DELAY = 3000;
    /**
     * 重连超时时间
     */
    public static final int DEFAULT_CONNECT_TIMEOUT = 10;
    /**
     * 写入超时时间
     */
    public static final int DEFAULT_WRITE_TIMEOUT = 0;
    /**
     * 读取超时时间
     */
    public static final int DEFAULT_READ_TIMEOUT = 10;

    /**
     * 是否需要缓存
     */
    private int mCache = CACHE_NONE;
    /**
     * 用于缓存的key
     */
    private String mCacheKey;
    /**
     * get时自动生成的key
     */
    private String mMethodGetGenerateKey;
    /**
     * 缓存时间 单位s
     */
    private int mCacheTimeout = 60;


    /**
     * 是否使用缓存
     *
     * @return true为使用
     */
    public boolean isCache() {
        return CACHE_NONE != mCache;
    }

    public int getCache() {
        return mCache;
    }

    /**
     * 设置是否使用缓存，默认不使用CACHE_NONE
     *
     * @param cache CACHE_NONE   CACHE_EXPIRE_DELETE   CACHE_EXPIRE_REQUEST
     */
    public void setCache(int cache) {
        mCache = cache;
    }

    /**
     * 自定义缓存key
     *
     * @param cacheKey
     */
    public void setCacheKey(String cacheKey) {
        mCacheKey = cacheKey;
    }

    /**
     * get时自动生成的key
     *
     * @return
     */
    public String getMethodGetGenerateKey() {
        return mMethodGetGenerateKey;
    }

    public void setMethodGetGenerateKey(String methodGetGenerateKey) {
        mMethodGetGenerateKey = methodGetGenerateKey;
    }

    public String getCacheKey() {
        return TextUtils.isEmpty(mCacheKey) ? mMethodGetGenerateKey : mCacheKey;
    }


    public int getCacheTimeout() {
        return mCacheTimeout;
    }

    public Type getTType() {
        return Utils.getType(this, 1);
    }

    /**
     * 默认60s
     *
     * @param cacheTimeout 自定义缓存超时时间
     */
    public void setCacheTimeout(int cacheTimeout) {
        mCacheTimeout = cacheTimeout;
    }


    /**
     * 设置service
     *
     * @param service
     * @return
     */
    @Override
    public abstract Observable<R> getObservable(S service);

    /**
     * 连网
     *
     * @param callBack
     */
    @Override
    public void execute(NetWorkCallback callBack) {
        if (callBack instanceof HttpDataCallback) {
            mSubscriber = NetworkManager.getInstance().execute(this, (HttpDataCallback) callBack);
        } else if (callBack instanceof HttpRespCallback) {
            mSubscriber = NetworkManager.getInstance().execute(this, (HttpRespCallback) callBack);
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

    @Override
    public String toString() {
        initConfig();
        return "HttpService{" +
                "mCustomBaseUrl='" + getCustomBaseUrl() + '\'' +
                ", mConnectTimeout=" + getConnectTimeout() +
                ", mReadTimeout=" + getReadTimeout() +
                ", mWriteTimeout=" + getWriteTimeout() +
                ", mRetryCount=" + getRetryCount() +
                ", mRetryDelay=" + getRetryDelay() +
                ", mRetryIncreaseDelay=" + getRetryIncreaseDelay() +
                ", mCache=" + mCache +
                ", mCacheKey='" + mCacheKey + '\'' +
                ", mMethodGetGenerateKey='" + mMethodGetGenerateKey + '\'' +
                ", mCacheTimeout=" + mCacheTimeout +
                '}';
    }

    /**
     * 初始化 超时时间等配置,
     */
    public void initConfig() {
        // 设置超时时间30S，默认为10S
        setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);

        // 初始化缓存策略
        setCache(CACHE_NONE);

        // 初始化重试次数
        setRetryCount(DEFAULT_RETRY_COUNT);
        // 初始化重试延迟时间
        setRetryDelay(DEFAULT_RETRY_DELAY);
        // 初始化重试叠加延迟时间
        setRetryIncreaseDelay(DEFAULT_RETRY_INCREASE_DELAY);

        // 初始化写入超时时间
        setWriteTimeout(DEFAULT_WRITE_TIMEOUT);
        // 初始化读取超时时间
        setReadTimeout(DEFAULT_READ_TIMEOUT);

    }
}
