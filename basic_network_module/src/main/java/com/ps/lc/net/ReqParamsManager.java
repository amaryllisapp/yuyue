package com.ps.lc.net;

import com.ps.lc.net.intercepter.ReqParamsInterceptor;
import com.ps.lc.net.intercepter.RequestHeadParamInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangwulin on 2017/1/4.
 * Email:zhangwulin@feitaikeji.com
 */

public class ReqParamsManager {
    private static ReqParamsManager instance;
    /**
     * 基本不变的Header 动态加的可以直接加上@Header("appToken") String appToken
     */
    private Map<String, String> mBaseHeader = new HashMap<>();
    /**
     * 基本不变的参数
     */
    private Map<String, String> mBaseParams = new HashMap<>();

    private ReqParamsManager() {
    }

    public static synchronized ReqParamsManager getInstance() {
        if (instance == null) {
            instance = new ReqParamsManager();
        }
        return instance;
    }

    /**
     * 初始化基本不变的Header和Params,在application时初始化
     * 动态加的可以直接加上@Header("appToken") String appToken
     *
     * @param baseHeader 基本头信息
     * @param baseParams 基本参数
     */
    public void initBaseHeader(Map<String, String> baseHeader, Map<String, String> baseParams) {
        initBaseHeader(baseHeader);
        this.mBaseParams.clear();
        if (null != baseParams) {
            this.mBaseParams.putAll(baseParams);
        }
    }

    /**
     * 初始化Header
     *
     * @param baseHeader
     */
    public void initBaseHeader(Map<String, String> baseHeader) {
        this.mBaseHeader.clear();
        if (null != baseHeader) {
            this.mBaseHeader.putAll(baseHeader);
        }
    }


    /**
     * 获取基本的头信息
     */
    private Map<String, String> getBaseHeader() {
        return mBaseHeader;
    }


    /**
     * 获取基本的Params
     */
    private Map<String, String> getBaseParams() {
        return mBaseParams;
    }


    /**
     * 返回请求参数拦截器
     *
     * @return
     */
    public ReqParamsInterceptor getInterceptor() {
        ReqParamsInterceptor interceptor = new RequestHeadParamInterceptor();
        interceptor.setHeaderParamsMap(getBaseHeader());
        interceptor.setParamsMap(getBaseParams());
        return interceptor;

    }
}
