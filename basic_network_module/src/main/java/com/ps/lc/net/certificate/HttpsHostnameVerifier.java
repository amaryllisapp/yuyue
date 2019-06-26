package com.ps.lc.net.certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.internal.tls.OkHostnameVerifier;

/**
 * Created by zhangwulin on 2016/12/16.
 * Email:zhangwulin@feitaikeji.com
 */

public class HttpsHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return OkHostnameVerifier.INSTANCE.verify(hostname, session);//用okhttp默认的
    }
}
