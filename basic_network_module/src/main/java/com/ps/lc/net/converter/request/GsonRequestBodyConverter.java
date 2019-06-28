package com.ps.lc.net.converter.request;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.ps.lc.net.service.BaseService;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 类名：GsonRequestBodyConverter
 * 描述：网络请求GSON转换类
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/26 19:19
 */
public class GsonRequestBodyConverter<T> extends BaseRequestBodyConverter<T> {
    protected final TypeAdapter<T> adapter;
    private Gson mGson;

    public GsonRequestBodyConverter(BaseService service, Gson gson, TypeAdapter<T> adapter) {
        super(service);
        mGson = gson;
        this.adapter = adapter;
    }

    /**
     * 请求网络时，将请求数据进行JSON转换处理
     *
     * @param value
     * @param mediaType
     * @return
     * @throws IOException
     */
    @Override
    protected RequestBody getConvertRequestBody(T value, MediaType mediaType) throws IOException {
        return RequestBody.create(mediaType, mGson.toJson(value));
    }


}