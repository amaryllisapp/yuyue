/*
 * Copyright (C) 2012-2017 feitaikeji Corporation. All rights reserved.
 */

package com.ps.lc.net.converter.request;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.ps.lc.net.service.BaseService;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by zhangwulin on 2017/1/14.
 * Email:zhangwulin@feitaikeji.com
 */

public class BaseGsonRequestBodyConverter<T> extends BaseRequestBodyConverter<T> {
    protected final TypeAdapter<T> adapter;
    private Gson mGson;

    public BaseGsonRequestBodyConverter(BaseService service, Gson gson, TypeAdapter<T> adapter) {
        super(service);
        mGson = gson;
        this.adapter = adapter;
    }

    @Override
    protected RequestBody getConvertRequestBody(T value, MediaType mediaType) throws IOException {
        return RequestBody.create(mediaType, mGson.toJson(value));

    }


}