package com.ps.lc.net.service;

import android.support.annotation.IntRange;

import com.ps.lc.net.HttpsUtils;
import com.ps.lc.net.INetwork;
import com.ps.lc.net.NetworkCode;
import com.ps.lc.net.Utils;
import com.ps.lc.net.certificate.HttpsCertificate;
import com.ps.lc.net.converter.BaseConverterFactory;
import com.ps.lc.net.converter.BaseGsonConverterFactory;
import com.ps.lc.net.encryption.NetworkEncryption;
import com.ps.lc.net.exception.ApiException;
import com.ps.lc.net.subscriber.ISubscriber;
import com.trello.rxlifecycle.LifecycleTransformer;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhangwulin on 2017/1/10.
 * Email:zhangwulin@feitaikeji.com
 */

public abstract class BaseService<S, R> implements INetwork {
    /**
     * https证书
     */
    private HttpsCertificate mHttpsCertificate;

    protected ISubscriber mSubscriber = null;
    /**
     * 加密器
     */
    private NetworkEncryption mEncryption;
    /**
     * 自定义转换器
     */
    private BaseConverterFactory mConverter;
    /**
     * 增加应用拦截器
     */
    private List<Interceptor> mInterceptors = null;
    /**
     * 增加网络拦截器
     */
    private List<Interceptor> mNetworkInterceptors = null;
    /**
     * rxLifecycle生命周期管理
     */
    private LifecycleTransformer<R> mLifecycleTransformer;
    /**
     * 自定义baseUrl不设置则用默认的
     */
    private String mCustomBaseUrl = null;
    /**
     * 连接超时时间 秒 0为使用默认的
     */
    private int mConnectTimeout = 10;
    /**
     * 读时间 秒 0为使用默认的
     */
    private int mReadTimeout = 10;
    /**
     * 写时间 秒 0为使用默认的
     */
    private int mWriteTimeout = 0;
    /**
     * retry次数 , 范围在 1-10
     */
    private int mRetryCount = 2;
    /**
     * retry延迟 毫秒
     */
    private long mRetryDelay = 3000;
    /**
     * retry叠加延迟 毫秒
     */
    private int mRetryIncreaseDelay = 3000;

    /**
     * rxLifecycle生命周期管理
     *
     * @return Transformer
     */
    public Observable.Transformer<R, R> getLifecycleTransformer() {
        if (null != mLifecycleTransformer) {
            return mLifecycleTransformer;
        }
        return new Observable.Transformer<R, R>() {
            @Override
            public Observable<R> call(Observable<R> tObservable) {
                return tObservable;
            }
        };
    }


    /**
     * 绑定rxActivity
     */
    public void setLifecycleTransformer(LifecycleTransformer<R> lifecycleTransformer) {
        mLifecycleTransformer = lifecycleTransformer;
    }

    /**
     * 是否使用密文，若使用了https是无需再自己加密的
     *
     * @return true为使用
     */
    public boolean isCipherText() {
        return false;
    }


    /**
     * 获取加密方法
     *
     * @return 加密器
     */
    public NetworkEncryption getNetworkEncryption() {
        return mEncryption;
    }

    /**
     * 设置加密方法
     *
     * @param encryption 加密器
     */
    public void setNetworkEncryption(NetworkEncryption encryption) {
        this.mEncryption = encryption;
    }

    /**
     * 获取自定义转换器，没有则返回默认的
     *
     * @return 转换器
     */
    public Converter.Factory getConverter() {
        return null == mConverter ? BaseGsonConverterFactory.create(this) : mConverter;
    }

    /**
     * 设置自定义转换器
     *
     * @param mConverter 自定义转换器
     */
    public void setConverter(BaseConverterFactory mConverter) {
        this.mConverter = mConverter;
    }

    /**
     * 设置https证书
     *
     * @param mHttpsCertificate https证书封装类
     */
    public void setHttpsCertificate(HttpsCertificate mHttpsCertificate) {
        this.mHttpsCertificate = mHttpsCertificate;
    }


    /**
     * 设置service
     *
     * @param service
     * @return
     */
    public abstract Observable<R> getObservable(S service);

    /**
     * 返回service
     *
     * @return
     */
    public Class<S> getServiceClass() {
        try {
            return (Class<S>) Utils.getType(this, 0);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 是否要默认参数，默认返回true
     *
     * @return true为要
     */
    public boolean hasDefReqParams() {
        return true;
    }

    /**
     * @param builder
     * @throws ApiException
     */
    public void checkInitHttps(OkHttpClient.Builder builder) throws ApiException {
        if (isTwoWayAuthenticationHttps()) {
            if (null == mHttpsCertificate) {
                throw NetworkCode.getHttpException(NetworkCode.EXCEPTION_11000);
            }
            HttpsUtils.setHttps(builder, mHttpsCertificate);
        }
    }

    /**
     * https是否使用双向验证
     *
     * @return true为使用 false则使用okhttp3自已的默认ssl验证
     */
    public boolean isTwoWayAuthenticationHttps() {
        return false;
    }

    /**
     * 订阅所属调度，若需要改变，覆盖此方法
     *
     * @return 默认在io
     */
    public Scheduler getSubscribeOn() {
        return Schedulers.io();
    }

    /**
     * 反订阅所属调度，若需要改变，覆盖此方法
     *
     * @return 默认在io
     */
    public Scheduler getUnsubscribeOn() {
        return Schedulers.io();
    }

    /**
     * 观察所属调度，若需要改变，覆盖此方法
     *
     * @return 默认在ui
     */
    public Scheduler getObserveOn() {
        return AndroidSchedulers.mainThread();
    }

    public ISubscriber getSubscriber() {
        return mSubscriber;
    }

    public void setSubscriber(ISubscriber mSubscriber) {
        this.mSubscriber = mSubscriber;
    }

    public int getConnectTimeout() {
        return mConnectTimeout;
    }

    public void setConnectTimeout(int mConnectTimeout) {
        this.mConnectTimeout = mConnectTimeout;
    }

    public int getReadTimeout() {
        return mReadTimeout;
    }

    public void setReadTimeout(int mReadTimeout) {
        this.mReadTimeout = mReadTimeout;
    }

    public int getWriteTimeout() {
        return mWriteTimeout;
    }

    public void setWriteTimeout(int mWriteTimeout) {
        this.mWriteTimeout = mWriteTimeout;
    }

    public int getRetryCount() {
        return mRetryCount;
    }

    public void setRetryCount(@IntRange(from = 0, to = 10) int mRetryCount) {
        this.mRetryCount = mRetryCount;
    }

    public long getRetryDelay() {
        return mRetryDelay;
    }

    public void setRetryDelay(long mRetryDelay) {
        this.mRetryDelay = mRetryDelay;
    }

    public int getRetryIncreaseDelay() {
        return mRetryIncreaseDelay;
    }

    public void setRetryIncreaseDelay(int mRetryIncreaseDelay) {
        this.mRetryIncreaseDelay = mRetryIncreaseDelay;
    }

    /**
     * 增加网络拦截器
     */
    public List<Interceptor> getNetworkInterceptors() {
        return mNetworkInterceptors;
    }

    /**
     * 增加网络拦截器
     */
    public void setNetworkInterceptors(List<Interceptor> networkInterceptors) {
        mNetworkInterceptors = networkInterceptors;
    }

    /**
     * 增加应用拦截器
     */
    public List<Interceptor> getInterceptors() {
        return mInterceptors;
    }

    /**
     * 增加应用拦截器
     */
    public void setInterceptors(List<Interceptor> interceptors) {
        mInterceptors = interceptors;
    }

    /**
     * 自定义baseUrl不设置则用默认的
     *
     * @return
     */
    public String getCustomBaseUrl() {
        return mCustomBaseUrl;
    }

    /**
     * 自定义baseUrl不设置则用默认的
     *
     * @param customBaseUrl
     */
    public void setCustomBaseUrl(String customBaseUrl) {
        this.mCustomBaseUrl = customBaseUrl;
    }


}
