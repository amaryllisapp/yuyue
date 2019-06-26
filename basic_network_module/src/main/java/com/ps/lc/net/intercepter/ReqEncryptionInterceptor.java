/*
 * Copyright (C) 2012-2017 feitaikeji Corporation. All rights reserved.
 */

package com.ps.lc.net.intercepter;

import android.util.Log;

import com.ps.lc.net.Utils;
import com.ps.lc.net.encryption.NetworkEncryption;
import com.ps.lc.net.service.BaseService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by zhangwulin on 2017/3/16.
 * Email:zhangwulin@feitaikeji.com
 */

public class ReqEncryptionInterceptor implements Interceptor {

    private NetworkEncryption mEncryption = null;

    public ReqEncryptionInterceptor(BaseService service) {
        if (service.isCipherText() && null != service.getNetworkEncryption()) {
            mEncryption = service.getNetworkEncryption();
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        if (null != mEncryption) {
            Request.Builder requestBuilder = request.newBuilder();

            RequestBody body = request.body();
            if (null != body) {//加密body
                requestBuilder.post(RequestBody.create(body.contentType(), mEncryption.encryptRequest(bodyToString(body))));
            }
            request = requestBuilder.build();
        }
        return chain.proceed(request);
    }


    private static String bodyToString(final RequestBody request) {
        Buffer buffer = null;
        try {
            final RequestBody copy = request;
            buffer = new Buffer();
            if (copy != null) {
                copy.writeTo(buffer);
            } else {
                return "";
            }
            return buffer.readUtf8();
        } catch (IOException e) {
            Log.getStackTraceString(e);
            return "";
        } finally {
            Utils.closeQuietly(buffer);
        }
    }


}