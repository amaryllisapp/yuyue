package com.ps.lc.net.converter.response;

import com.ps.lc.net.encryption.NetworkEncryption;
import com.ps.lc.net.service.BaseService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 类名：BaseResponseBodyConverter
 * 描述：转换基类（数据在这里被截获并判断是否进行解密处理）
 * <p>
 * TODO：这里的BaseService体量有点大，需要进行瘦身处理
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/26 19:17
 */
public abstract class BaseResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    protected final BaseService mService;

    public BaseResponseBodyConverter(BaseService service) {
        mService = service;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        if (mService.isCipherText() && null != mService.getNetworkEncryption()) {
            return getCipherTextConvertResponseBody(value, mService.getNetworkEncryption());
        } else {
            return getConvertResponseBody(value);
        }
    }

    /**
     * 使用加密时返回
     */
    protected abstract T getCipherTextConvertResponseBody(ResponseBody value, NetworkEncryption encryption) throws IOException;

    /**
     * 不使用加密
     *
     * @param value
     * @return
     */
    protected abstract T getConvertResponseBody(ResponseBody value) throws IOException;
}
