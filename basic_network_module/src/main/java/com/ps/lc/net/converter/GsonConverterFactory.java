package com.ps.lc.net.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.ps.lc.net.converter.request.GsonRequestBodyConverter;
import com.ps.lc.net.converter.response.GsonResponseBodyConverter;
import com.ps.lc.net.service.BaseService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 类名：GsonConverterFactory
 * <p>
 * 描述：GSON 转换器
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/26 19:10
 */
public class GsonConverterFactory extends BaseConverterFactory {

    protected final Gson gson;

    /**
     * 创建GSON转换器
     *
     * @param service
     * @return
     */
    public static GsonConverterFactory create(BaseService service) {
        return new GsonConverterFactory(service);
    }

    protected GsonConverterFactory(BaseService service) {
        super(service);
        gson = new Gson();
    }

    /**
     * 响应时GSON数据转换
     *
     * @param type
     * @param annotations
     * @param retrofit
     * @return
     */
    @Override
    protected Converter<ResponseBody, ?> getResponseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonResponseBodyConverter<>(mService, gson, type, adapter);
    }

    /**
     * 请求是GSON数据转换
     *
     * @param type
     * @param parameterAnnotations
     * @param methodAnnotations
     * @param retrofit
     * @return
     */
    @Override
    protected Converter<?, RequestBody> getRequestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new GsonRequestBodyConverter<>(mService, gson, gson.getAdapter(TypeToken.get(type)));
    }


}
