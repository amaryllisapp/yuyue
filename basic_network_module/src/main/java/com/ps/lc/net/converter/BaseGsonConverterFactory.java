package com.ps.lc.net.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.ps.lc.net.converter.request.BaseGsonRequestBodyConverter;
import com.ps.lc.net.converter.response.BaseGsonResponseBodyConverter;
import com.ps.lc.net.service.BaseService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by zhangwulin on 2016/11/2.
 * Eamil zhangwulin@feitaikeji.com
 * 转换器
 */

public class BaseGsonConverterFactory extends BaseConverterFactory {

    protected final Gson gson;

    public static BaseGsonConverterFactory create(BaseService service) {
        return new BaseGsonConverterFactory(service);
    }

    protected BaseGsonConverterFactory(BaseService service) {
        super(service);
        gson = new Gson();
    }


    @Override
    protected Converter<ResponseBody, ?> getResponseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new BaseGsonResponseBodyConverter<>(mService, gson, type, adapter);
    }

    @Override
    protected Converter<?, RequestBody> getRequestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new BaseGsonRequestBodyConverter<>(mService, gson, gson.getAdapter(TypeToken.get(type)));
    }


}
