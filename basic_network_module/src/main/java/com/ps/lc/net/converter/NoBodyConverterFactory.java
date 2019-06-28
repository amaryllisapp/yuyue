package com.ps.lc.net.converter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 *
 * 类名：NoBodyConverterFactory
 * 描述： 处理body为空
 *
 * @author liucheng - liucheng@xhg.com
 *
 * @date 2019/6/26 19:10
 */
public class NoBodyConverterFactory extends Converter.Factory {

    public static final NoBodyConverterFactory create() {
        return new NoBodyConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
        return new Converter<ResponseBody, Object>() {
            @Override
            public Object convert(ResponseBody body) throws IOException {
                if (body.contentLength() == 0){
                    return null;
                }
                return delegate.convert(body);
            }
        };
    }
}
