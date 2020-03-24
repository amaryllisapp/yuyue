package com.ps.lc.net.encryption;

/**
 * Created by zhangwulin on 2017/1/6.
 * Email:zhangwulin@feitaikeji.com
 * 缓存加密解密接口
 */

public interface ICacheEncryption {
    /**
     * 加密
     */
    String encrypt(String str);

    /**
     * 解密
     */
    String decrypt(String str);
}
