/*
 * Copyright (C) 2012-2017 feitaikeji Corporation. All rights reserved.
 */

package com.ps.lc.net.converter.response;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.ps.lc.net.Utils;
import com.ps.lc.net.encryption.NetworkEncryption;
import com.ps.lc.net.service.BaseService;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

/**
 * Created by zhangwulin on 2017/1/14.
 * Email:zhangwulin@feitaikeji.com
 */

public class BaseGsonResponseBodyConverter<T> extends BaseResponseBodyConverter<T> {

    public static final String TAG = "BaseGsonResponse";

    protected final Gson gson;
    protected final TypeAdapter<T> adapter;
    protected final Type mType;

    public BaseGsonResponseBodyConverter(BaseService service, Gson gson, Type type, TypeAdapter<T> adapter) {
        super(service);
        this.gson = gson;
        this.adapter = adapter;
        mType = type;
    }

    @Override
    protected T getCipherTextConvertResponseBody(ResponseBody value, NetworkEncryption encryption) throws IOException {
        try {
            return encryption.decryptResponse(value.string(), mType);
        } finally {
            Utils.closeQuietly(value);
        }
    }

    @Override
    protected T getConvertResponseBody(ResponseBody value) throws IOException {

        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            return adapter.read(jsonReader);
        } finally {
            Utils.closeQuietly(value);
        }
    }

}
