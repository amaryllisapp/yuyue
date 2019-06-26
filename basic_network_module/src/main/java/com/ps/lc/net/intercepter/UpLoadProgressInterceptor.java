package com.ps.lc.net.intercepter;


import com.ps.lc.net.callback.UploadCallback;
import com.ps.lc.net.service.UploadRequestBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhangwulin on 2017/1/14.
 * Email:zhangwulin@feitaikeji.com
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
