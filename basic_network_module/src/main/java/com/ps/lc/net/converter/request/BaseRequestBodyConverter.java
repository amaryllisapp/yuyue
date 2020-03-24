package com.ps.lc.net.converter.request;

import com.ps.lc.net.service.BaseService;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * 类名：BaseRequestBodyConverter
 * 描述：请求转换类基类（请求网络之前进行截获处理）
 *
 * TODO：MEDIA_TYPE方式需要考虑其他方式（可以通过BaseService进行获取）
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/26 19:21
 */
public abstract class BaseRequestBodyConverter<T> implements Converter<T, RequestBody> {

    private final BaseService mService;

    protected static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    public BaseRequestBodyConverter(BaseService service) {
        mService = service;
    }


    @Override
    public RequestBody convert(T value) throws IOException {
        return getConvertRequestBody(value, getMediaType());
    }

    /**
     * 若需要改MediaType，覆盖这个方法
     *
     * @return
     */
    protected MediaType getMediaType() {
        return MEDIA_TYPE;
    }

    /**
     * 请求加密直接在拦截器做
     */
    protected abstract RequestBody getConvertRequestBody(T value, MediaType mediaType) throws IOException;
}