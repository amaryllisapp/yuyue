/*
 * Copyright (C) 2012-2017 feitaikeji Corporation. All rights reserved.
 */

package com.ps.lc.net.callback;


import com.ps.lc.net.service.BaseResponse;

/**
 * Created by zhangwulin on 2017/1/17.
 * Email:zhangwulin@feitaikeji.com
 * 约定：不返回data只返回resp,用于判断，没缓存
 */

public interface HttpRespCallback<T extends BaseResponse> extends NetWorkCallback {
    /**
     * 开始
     */
    void onHttpStart();

    /**
     * 结束
     */
    void onHttpCompleted();

    /**
     * 请求成功
     *
     * @param bean 不为null,null认为是请求失败
     */
    void onHttpSuccess(T bean);

    /**
     * 请求失败
     *
     * @param errorMsg 失败信息
     * @param code     失败码
     */
    void onHttpFailure(String errorMsg, String code);

    /**
     * 请求失败
     *
     * @param errorMsg 失败信息
     * @param code     失败码
     */
    void onHttpFailureWithData(String errorMsg, String code, Object object);
}
