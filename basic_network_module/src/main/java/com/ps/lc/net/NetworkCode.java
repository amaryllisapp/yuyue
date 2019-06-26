package com.ps.lc.net;

import com.ps.lc.net.exception.ApiException;
import com.ps.lc.net.exception.HttpException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangwulin on 2017/12/4.
 * Email:zhangwulin@feitaikeji.com
 */

public class NetworkCode {
    /*
       前缀
     */
    public static final String PREFIX_NATIVE = "NATIVE_";
    /*
       https验证失败
     */
    public static final String EXCEPTION_10000 = PREFIX_NATIVE + "EXCEPTION_10000";
    /*
       强校验证书，https没有证书，严格要求要有证书,绝不信任所有证书
     */
    public static final String EXCEPTION_11000 = PREFIX_NATIVE + "EXCEPTION_11000";
    /*
      未设置baseUrl或者格式不对
     */
    public static final String EXCEPTION_12000 = PREFIX_NATIVE + "EXCEPTION_12000";
    /*
      设置了密文形式，但没有加密器
     */
    public static final String EXCEPTION_13000 = PREFIX_NATIVE + "EXCEPTION_13000";
    /*
      加密解密出错
     */
    public static final String EXCEPTION_14000 = PREFIX_NATIVE + "EXCEPTION_14000";
    /*
      响应的bean为null
     */
    public static final String RESPONSE_E20000 = PREFIX_NATIVE + "RESPONSE_E20000";
    /*
      请求超时
     */
    public static final String RESPONSE_E21000 = PREFIX_NATIVE + "RESPONSE_E21000";
    /*
      ConnectException一般是被拒
     */
    public static final String RESPONSE_E22000 = PREFIX_NATIVE + "RESPONSE_E22000";
    /*
      没网络
     */
    public static final String RESPONSE_E23000 = PREFIX_NATIVE + "RESPONSE_E23000";
    /*
      未知异常
     */
    public static final String RESPONSE_E30000 = PREFIX_NATIVE + "RESPONSE_E30000";
    /*
      类型错误
     */
    public static final String RESPONSE_E40000 = PREFIX_NATIVE + "RESPONSE_E40000";

    /**
     *  网络错误
     */
    public static final String RESPONSE_E50000 = PREFIX_NATIVE + "RESPONSE_E50000";

    /**
     *  连接路由失败
     */
    public static final String RESPONSE_E51000 = PREFIX_NATIVE + "RESPONSE_E51000";

    /**
     *  域名解析错误
     */
    public static final String RESPONSE_E52000 = PREFIX_NATIVE + "RESPONSE_E52000";


    private static final Map<String, String> sCode = new HashMap<>();

    private static final String NETWORK_CONNECT_ERROR = "网络异常，请稍后再试";
    private static final String NETWORK_DATA_ERROR = "网络异常，请稍后再试";
    private static final String NETWORK_CONNECT_TIMEOUT = "网络异常，请稍后再试";
    private static final String NETWORK_UNKNOWN = "网络异常，请稍后再试";

    static {
        sCode.put(EXCEPTION_10000, NETWORK_DATA_ERROR);
        sCode.put(EXCEPTION_11000, NETWORK_DATA_ERROR);
        sCode.put(EXCEPTION_12000, NETWORK_DATA_ERROR);
        sCode.put(EXCEPTION_13000, NETWORK_DATA_ERROR);
        sCode.put(EXCEPTION_14000, NETWORK_DATA_ERROR);
        sCode.put(RESPONSE_E20000, NETWORK_DATA_ERROR);
        sCode.put(RESPONSE_E21000, NETWORK_CONNECT_TIMEOUT);
        sCode.put(RESPONSE_E22000, NETWORK_CONNECT_ERROR);
        sCode.put(RESPONSE_E23000, NETWORK_CONNECT_ERROR);
        sCode.put(RESPONSE_E30000, NETWORK_UNKNOWN);
        sCode.put(RESPONSE_E40000, NETWORK_DATA_ERROR);
        sCode.put(RESPONSE_E50000, NETWORK_CONNECT_ERROR);
        sCode.put(RESPONSE_E51000, NETWORK_CONNECT_ERROR);
        sCode.put(RESPONSE_E52000, NETWORK_CONNECT_ERROR);
    }

    private NetworkCode() {
    }

    /**
     * 是否本地的响应码
     */
    public static boolean isNativeCode(String code) {
        return null != code && code.startsWith(PREFIX_NATIVE);
    }

    /**
     * 设置响应码及对应描述
     * 也用于更改api默认的响应码描述
     *
     * @param code 响应码
     * @param msg  描述
     */
    public static void putCodeDesc(String code, String msg) {
        sCode.put(code, msg);
    }

    /**
     * 获取响应码描述
     *
     * @param code 响应码
     * @return 描述
     */
    public static String getCodeDesc(String code) {
        return sCode.get(code);
    }

    /**
     * 根据code获取 HttpException
     */
    public static HttpException getHttpException(String code) {
        return new HttpException(code, getCodeDesc(code));
    }

    /**
     * 根据code获取 ApiException
     */
    public static ApiException getApiException(String code) {
        return new ApiException(code, getCodeDesc(code));
    }
}
