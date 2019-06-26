/*
 * Copyright (C) 2012-2017 feitaikeji Corporation. All rights reserved.
 */

package com.ps.lc.net.service;

/**
 * Created by zhangwulin on 2017/1/3.
 * Email:zhangwulin@feitaikeji.com
 * 网格响应抽象类
 * 如果T想返回json的话，直接用JsonElement
 */

public abstract class BaseResponse<T> {
    /**
     * 是否请求成功，这个是接口协的判断，若没直接返回true就是
     *
     * @return true为成功 基本接口协议成功也成功
     */
    public abstract boolean isBaseApiSuccess();

    /**
     * 这个是这个接口是否成功的判断，若没直接返回true就是
     *
     * @return true为成功 接口业务也成功
     */
    public abstract boolean isSuccess();


    /**
     * 返回响应码，若不是String 转一下就行
     *
     * @return 响应码
     */
    public abstract String getBaseApiCode();

    /**
     * 返回响应码描述，这里主要的是错误信息等
     *
     * @return 响应码描述
     */
    public abstract String getBaseApiCodeDesc();

    /**
     * 业务错误编码
     *
     * @return 若不是String 转一下就行
     */
    public abstract String getTCode();

    /**
     * 业务错误信息
     *
     * @return
     */
    public abstract String getTCodeMsg();

    /**
     * 返回pojo实体类,api的主体
     *
     * @return pojo
     */
    public abstract T getTData();

}
