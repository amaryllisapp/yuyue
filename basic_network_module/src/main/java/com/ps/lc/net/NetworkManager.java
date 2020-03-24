package com.ps.lc.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.URLUtil;

import com.ps.lc.net.adapter.RxJavaCallAdapterFactory;
import com.ps.lc.net.cache.DiskLruCacheHelper;
import com.ps.lc.net.callback.DownloadCallback;
import com.ps.lc.net.callback.HttpDataCallback;
import com.ps.lc.net.callback.HttpRespCallback;
import com.ps.lc.net.callback.UploadCallback;
import com.ps.lc.net.encryption.ICacheEncryption;
import com.ps.lc.net.exception.ApiException;
import com.ps.lc.net.fun.FuncDownload;
import com.ps.lc.net.fun.FuncRetry;
import com.ps.lc.net.fun.FuncService;
import com.ps.lc.net.intercepter.ReqEncryptionInterceptor;
import com.ps.lc.net.intercepter.UpLoadProgressInterceptor;
import retrofit2.RetrofitServiceMapping;
import com.ps.lc.net.service.BaseResponse;
import com.ps.lc.net.service.BaseService;
import com.ps.lc.net.service.DownloadService;
import com.ps.lc.net.service.HttpService;
import com.ps.lc.net.service.UploadService;
import com.ps.lc.net.subscriber.BaseSubscriber;
import com.ps.lc.net.subscriber.DownloadSubscriber;
import com.ps.lc.net.subscriber.HttpDataSubscriber;
import com.ps.lc.net.subscriber.HttpRespSubscriber;
import com.ps.lc.net.subscriber.ISubscriber;
import com.ps.lc.net.subscriber.UploadSubscriber;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 *
 * 类名：NetworkManager
 * 描述：网络管理类
 *
 * @author liucheng - liucheng@xhg.com
 *
 * @date 2019/6/26 17:29
 */
public class NetworkManager {

    /**
     * 通过MD5加密参数 匹配 对应的Retrofit
     */
    private Map<String, Retrofit> mRetrofitMap = new ConcurrentHashMap<>();
    /**
     * baseUrl，在application时初始化，一般不改的
     */
    private String mBaseUrl;
    private Context mAppContext;
    private File cacheDir;
    private String mCacheDirName = "pp_net_cache";
    private ICacheEncryption mCacheEncryption;
    private long mCacheMaxSize = 5 * 1024 * 1024L;
    private Vector<ISubscriber> mSubscribers = new Vector<>();
    private boolean mShowLog = false;

    private NetworkManager() {
    }

    /**
     * 获取实例
     */
    public static NetworkManager getInstance() {
        return NetworkManagerHolder.instance;
    }

    /**
     * 初始化，在application调用
     */
    public void init(Context context, String baseUrl) {
        this.init(context, baseUrl, null);
    }

    /**
     * 初始化，在application调用
     */
    public void init(Context context, String baseUrl, ICacheEncryption encryption) {
        this.init(context, baseUrl, encryption, false);
    }

    /**
     * 初始化，在application调用
     */
    public void init(Context context, String baseUrl, ICacheEncryption encryption, boolean showLog) {
        mAppContext = context.getApplicationContext();
        mCacheEncryption = encryption;
        mShowLog = showLog;
        mBaseUrl = baseUrl;
        cacheDir = new File(Utils.getCacheDir(mAppContext), mCacheDirName);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }


    /**
     * 联网返回的是有data
     *
     * @return ISubscriber可以用于取消连网
     * @throws ApiException
     */
    public <S, T, R extends BaseResponse<T>> ISubscriber execute(final HttpService<S, R> service, HttpDataCallback<T> callBack) {
        if (!Utils.isNetwork(mAppContext, service.isCache())) {
            if (null != callBack) {
                callBack.onHttpFailure(NetworkCode.getCodeDesc(NetworkCode.RESPONSE_E23000), NetworkCode.RESPONSE_E23000);
                callBack.onHttpCompleted();
            }
            return null;
        }
        DiskLruCacheHelper diskLruCacheHelper = new DiskLruCacheHelper(cacheDir, mCacheMaxSize);
        diskLruCacheHelper.setCacheEncyption(mCacheEncryption);
        HttpDataSubscriber<T, R> httpDataSubscriber = new HttpDataSubscriber<>(service, diskLruCacheHelper, callBack);
        execute(getRetrofit(service), service, new FuncService<R>(), httpDataSubscriber);
        return httpDataSubscriber;
    }

    /**
     * 联网返回的是没有data，只用于判断是否成功的，返回的是resp
     *
     * @return ISubscriber可以用于取消连网
     * @throws ApiException
     */
    public <S, R extends BaseResponse> ISubscriber execute(final HttpService<S, R> service, HttpRespCallback<R> callBack) {
        if (!Utils.isNetwork(mAppContext, false)) {
            if (null != callBack) {
                callBack.onHttpFailure(NetworkCode.getCodeDesc(NetworkCode.RESPONSE_E23000), NetworkCode.RESPONSE_E23000);
                callBack.onHttpCompleted();
            }
            return null;
        }
        HttpRespSubscriber<R> httpRespSubscriber = new HttpRespSubscriber<>(callBack);
        execute(getRetrofit(service), service, new FuncService<R>(), httpRespSubscriber);
        return httpRespSubscriber;
    }

    /**
     * 下载
     */
    public ISubscriber download(DownloadService service, DownloadCallback callBack) {
        if (!Utils.isNetwork(mAppContext, false)) {
            if (null != callBack) {
                callBack.onDownloadCompleted(null, NetworkCode.getCodeDesc(NetworkCode.RESPONSE_E23000));
            }
            return null;
        }
        DownloadSubscriber subscriber = new DownloadSubscriber(callBack);
        executeDownload(buildRetrofit(service, null), service, new FuncDownload(service, subscriber), subscriber);
        return subscriber;
    }


    /**
     * 上传,直接返回resp，有data直接getData就行了
     */
    public <S, R extends BaseResponse> ISubscriber upload(final UploadService<S, R> service, UploadCallback<R> callBack) {
        if (!Utils.isNetwork(mAppContext, false)) {
            if (null != callBack) {
                callBack.onUploadFailure(NetworkCode.getCodeDesc(NetworkCode.RESPONSE_E23000), NetworkCode.RESPONSE_E23000);
            }
            return null;
        }
        UploadSubscriber<R> uploadSubscriber = new UploadSubscriber<>(callBack);
        execute(buildRetrofit(service, new UpLoadProgressInterceptor(callBack)), service, new FuncService<R>(), uploadSubscriber);
        return uploadSubscriber;
    }

    /**
     * 执行连网
     */
    private <S, R, T> void execute(Retrofit retrofit, final BaseService<S, R> service, Func1<R, T> func1, BaseSubscriber<T> baseSubscriber) {
        try {
            mSubscribers.add(baseSubscriber);
            S reqService = RetrofitServiceMapping.mapping(retrofit, service.getServiceClass()).create(service.getServiceClass());
            service.getObservable(reqService).compose(service.getLifecycleTransformer())
                    .subscribeOn(service.getSubscribeOn())
                    .unsubscribeOn(service.getUnsubscribeOn())
                    .observeOn(service.getObserveOn())
                    .retryWhen(new FuncRetry(service))
                    .map(func1)
                    .subscribe(baseSubscriber);
        } catch (Exception e) {
            Log.getStackTraceString(e);
            mSubscribers.remove(baseSubscriber);
        }
    }

    private <S, R, T> void executeDownload(Retrofit retrofit, final BaseService<S, R> service, Func1<R, T> func1, BaseSubscriber<T> baseSubscriber) {
        try {
            mSubscribers.add(baseSubscriber);
            S reqService = retrofit.create(service.getServiceClass());
            service.getObservable(reqService).compose(service.getLifecycleTransformer())
                    .subscribeOn(service.getSubscribeOn())
                    .unsubscribeOn(service.getUnsubscribeOn())
                    .observeOn(Schedulers.immediate())
                    .retryWhen(new FuncRetry(service))
                    .map(func1)
                    .subscribe(baseSubscriber);
        } catch (Exception e) {
            e.printStackTrace();
            mSubscribers.remove(baseSubscriber);
        }
    }

    /**
     * 根据service的参数获取对应的Retrofit对象
     */
    private Retrofit getRetrofit(BaseService service) {
        String decode = Md5Utils.getMD5(service.toString());
        Retrofit mRetrofit = mRetrofitMap.get(decode);
        if (mRetrofit == null) {
            mRetrofit = buildRetrofit(service, null);
            mRetrofitMap.put(decode, mRetrofit);
        }
        return mRetrofit;
    }

    /**
     * 建立retrofit
     */
    private Retrofit buildRetrofit(BaseService baseService, Interceptor interceptor) throws ApiException {
        if (!URLUtil.isHttpsUrl(mBaseUrl) && !URLUtil.isHttpUrl(mBaseUrl)) {
            throw NetworkCode.getHttpException(NetworkCode.EXCEPTION_12000);
        }
        if (baseService.isCipherText() && null == baseService.getNetworkEncryption()) {
            throw NetworkCode.getApiException(NetworkCode.EXCEPTION_13000);
        }
        String baseUrl = TextUtils.isEmpty(baseService.getCustomBaseUrl()) ? mBaseUrl : baseService.getCustomBaseUrl();
        String code = "/";
        if (null != baseUrl && !baseUrl.endsWith(code)) {
            baseUrl += code;
        } else if (null == baseUrl) {
            throw NetworkCode.getHttpException(NetworkCode.EXCEPTION_12000);
        }
        return new Retrofit.Builder()
                .client(buildOkHttpClient(baseService, interceptor))
                .addConverterFactory(baseService.getConverter())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create(baseService))
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * 建立okHttpclient,不单例考虑到动态配置的一些东西,不同时不一样，反正线程池okhttp中默认只有一个
     */
    private OkHttpClient buildOkHttpClient(BaseService baseService, Interceptor interceptor) throws ApiException {
        //3.4.0后 okhttp自动支持gzip，不用手动写
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 设置okhttp的连接池保活时间为1秒,默认是5分钟,如果在5分钟之内创建超过2000个连接就报内存溢出
        builder.connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS));
        builder.sslSocketFactory(HttpsUtils.createSSLSocketFactory());
        if (baseService.getConnectTimeout() > 0) {
            builder.connectTimeout(baseService.getConnectTimeout(), TimeUnit.SECONDS);
        }
        if (baseService.getReadTimeout() > 0) {
            builder.readTimeout(baseService.getReadTimeout(), TimeUnit.SECONDS);
        }
        if (baseService.getWriteTimeout() > 0) {
            builder.writeTimeout(baseService.getWriteTimeout(), TimeUnit.SECONDS);
        }
        if (baseService.hasDefReqParams()) {
            builder.addInterceptor(ReqParamsManager.getInstance().getInterceptor());
        }
        if (baseService.isCipherText() && null != baseService.getNetworkEncryption()) {
            builder.addInterceptor(new ReqEncryptionInterceptor(baseService));
        }
        if (null != interceptor) {
            builder.addInterceptor(interceptor);
        }
        List list = baseService.getInterceptors();
        if (null != list) {
            for (Object interceptor1 : list) {
                builder.addInterceptor((Interceptor) interceptor1);
            }
        }
        list = baseService.getNetworkInterceptors();
        if (null != list) {
            for (Object interceptor1 : list) {
                builder.addNetworkInterceptor((Interceptor) interceptor1);
            }
        }
        if (mShowLog) {
            builder.addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return builder.build();
    }


    public void setShowLog(boolean showLog) {
        this.mShowLog = showLog;
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }

    /**
     * @param baseUrl
     */
    public void setBaseUrl(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    public String getCacheDirName() {
        return mCacheDirName;
    }

    public void setCacheDirName(String cacheDirName) {
        this.mCacheDirName = cacheDirName;
    }

    public long getCacheMaxSize() {
        return mCacheMaxSize;
    }

    public void setCacheMaxSize(long mCacheMacSize) {
        this.mCacheMaxSize = mCacheMacSize;
    }


    static class NetworkManagerHolder {
        private static final NetworkManager instance = new NetworkManager();

        private NetworkManagerHolder() {
        }

    }

    public void removeSubscribar(ISubscriber subscriber) {
        if (null != subscriber) {
            subscriber.destroy();
            mSubscribers.remove(subscriber);
            subscriber = null;
        }
    }

    /**
     * 取消所有请求
     */
    public void evictAll() {
        Iterator<ISubscriber> iterator = mSubscribers.iterator();
        ISubscriber subscriber = null;
        while (iterator.hasNext()) {
            subscriber = iterator.next();
            subscriber.destroy();
            iterator.remove();
            subscriber = null;
        }
    }
}
