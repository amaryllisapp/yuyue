package com.ps.lc.net.encryption;

import java.lang.reflect.Type;

/**
 * Created by zhangwulin on 2017/1/4.
 * Email:zhangwulin@feitaikeji.com
 * 加密解密直接开放给外面处理，因不同项目有不同的加密解密方式
 */

public abstract class NetworkEncryption {
    /**
     * 加密请求
     *
     * @param jsonStr 请求对象转用的jsonString
     * @return jsonString
     */
    public abstract String encryptRequest(String jsonStr);

    /**
     * 响应解密
     *
     * @param respStr 响应后返回的字符串
     * @param type    需要转换的类型
     */
    public abstract <T> T decryptResponse(String respStr, Type type);
}
