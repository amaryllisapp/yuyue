package com.ps.lc.net.intercepter;


import com.ps.lc.net.callback.UploadCallback;
import com.ps.lc.net.service.UploadRequestBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 类名：UpLoadProgressInterceptor
 * 描述：上传拦截器（用于回传上传情况）
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/26 19:36
 */
public class UpLoadProgressInterceptor implements Interceptor {
    private UploadCallback mUploadCallback;

    public UpLoadProgressInterceptor(UploadCallback uploadCallback) {
        mUploadCallback = uploadCallback;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        if (originalRequest.body() == null) {
            return chain.proceed(originalRequest);
        }

        Request progressRequest = originalRequest.newBuilder()
                .method(originalRequest.method(), new UploadRequestBody(originalRequest.body(), mUploadCallback))
                .build();

        return chain.proceed(progressRequest);
    }
}
