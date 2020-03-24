package com.ps.lc.net.service;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Email:zhangwulin@feitaikeji.com
 *
 * @author zhangwulin
 * @date 2017/1/10
 */

public interface IDownloadService {
    /**
     * 下载，没用断点
     *
     * @param url 下载地址
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
