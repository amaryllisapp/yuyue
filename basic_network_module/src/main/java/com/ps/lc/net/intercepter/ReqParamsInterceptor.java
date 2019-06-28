package com.ps.lc.net.intercepter;

import android.util.Log;

import com.ps.lc.net.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 类名：ReqParamsInterceptor
 * 描述：请求参数拦截器，包含请求头和请求参数两部分
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/26 19:32
 */
public abstract class ReqParamsInterceptor implements Interceptor {

    private Map<String, String> paramsMap = new HashMap<>();
    private Map<String, String> headerParamsMap = new HashMap<>();

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        // 动态添加头部参数集合和参数集合，保证每次请求的请求头都是动态最新获取
        Map<String, String> HParamsMap = initHeaderParamsMap();
        if (headerParamsMap != null) {
            this.headerParamsMap = HParamsMap;
        }

        Map<String, String> ParamsMap = initParamsMap();
        if (paramsMap != null) {
            this.paramsMap = ParamsMap;
        }

        // header
        Headers.Builder headerBuilder = request.headers().newBuilder();
        if (headerParamsMap != null && headerParamsMap.size() > 0) {
            Iterator<Map.Entry<String, String>> iterator = headerParamsMap.entrySet().iterator();
            Map.Entry<String, String> entry;
            while (iterator.hasNext()) {
                entry = iterator.next();
                headerBuilder.add(entry.getKey(), entry.getValue());
            }
            requestBuilder.headers(headerBuilder.build());
        }

        //params
        if (paramsMap != null && paramsMap.size() > 0) {
            RequestBody body = request.body();
            if (null != body) {
                if (body instanceof FormBody) {
                    addParams2FormBody(request, requestBuilder, paramsMap);
                } else if (body instanceof MultipartBody) {
                    addParams2MultipartBody(request, requestBuilder, paramsMap);
                } else {
                    addParams2Url(request, requestBuilder, paramsMap);
                }
            } else {
                addParams2Url(request, requestBuilder, paramsMap);
            }
        }


        request = requestBuilder.build();
        return chain.proceed(request);
    }

    /**
     * 添加参数到url
     *
     * @param request
     * @param requestBuilder
     * @param paramsMap
     */
    private void addParams2Url(Request request, Request.Builder requestBuilder, Map<String, String> paramsMap) {
        HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
        Iterator iterator = paramsMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) iterator.next();
            httpUrlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        requestBuilder.url(httpUrlBuilder.build());
    }

    /**
     * 为FormBody添加参数
     * //TODO:requestBuilder.post不能直接写，隐患太大
     *
     * @param request
     * @param requestBuilder
     * @param paramsMap
     */
    private void addParams2FormBody(Request request, Request.Builder requestBuilder, Map<String, String> paramsMap) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            formBodyBuilder.add(entry.getKey(), entry.getValue());
        }

        FormBody formBody = formBodyBuilder.build();
        if (null != formBody) {
            String postBodyString = bodyToString(request.body());
            postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
            requestBuilder.post(RequestBody.create(formBody.contentType(), postBodyString));
        }
    }

    /**
     * 为MultipartBody类型请求体添加参数
     *
     * @param request
     * @param requestBuilder
     * @param paramsMap
     */
    private void addParams2MultipartBody(Request request, Request.Builder requestBuilder, Map<String, String> paramsMap) {
        MultipartBody body = (MultipartBody) request.body();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            builder.addFormDataPart(entry.getKey(), entry.getValue());
        }
        for (int i = 0; i < body.size(); i++) {
            builder.addPart(body.part(i));
        }
        requestBuilder.method(request.method(), builder.build());
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


    public abstract Map<String, String> initHeaderParamsMap();

    public abstract Map<String, String> initParamsMap();


    public void setParamsMap(Map<String, String> paramsMap) {
        clearParamsMap();
        this.paramsMap = paramsMap;
    }

    public void setHeaderParamsMap(Map<String, String> headerParamsMap) {
        clearHeaderParamsMap();
        this.headerParamsMap = headerParamsMap;
    }

    public void clearParamsMap() {
        this.paramsMap.clear();
    }

    public void clearHeaderParamsMap() {
        this.headerParamsMap.clear();
    }
}
