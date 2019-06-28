package com.ps.lc.net.converter;


import com.ps.lc.net.service.BaseService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 *
 * 类名：BaseConverterFactory
 * 描述：基础Converter.Factory类,便于各种不同的JSON解析工具继承并实现
 *
 * @author liucheng - liucheng@xhg.com
 *
 * @date 2019/6/26 19:09
 */
public abstract class BaseConverterFactory extends Converter.Factory {

    protected BaseService mService;

    public BaseConverterFactory(BaseService service) {
        this.mService = service;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {

        return getResponseBodyConverter(type, annotations, retrofit);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations, Retrofit retrofit) {
        return getRequestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

    protected abstract Converter<ResponseBody, ?> getResponseBodyConverter(Type type, Annotation[] annotations,
                                                                           Retrofit retrofit);

    protected abstract Converter<?, RequestBody> getRequestBodyConverter(Type type, Annotation[] parameterAnnotations,
                                                                         Annotation[] methodAnnotations, Retrofit retrofit);

}
