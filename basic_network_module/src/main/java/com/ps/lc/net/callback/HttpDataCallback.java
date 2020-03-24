package com.ps.lc.net.callback;

/**
 * Created by zhangwulin on 2017/1/4.
 * Email:zhangwulin@feitaikeji.com
 * 响应回调,到时根据业务可以实现这个接口，把相同方法统一处理
 */

public interface HttpDataCallback<T> extends NetWorkCallback {
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
     * 缓存回调
     *
     * @param bean 不为null
     */
    void onHttpCache(T bean);

    /**
     * 请求失败
     *
     * @param errorMsg 失败信息
     * @param code     失败码
     */
    void onHttpFailureWithData(String errorMsg, String code, Object object);


}
