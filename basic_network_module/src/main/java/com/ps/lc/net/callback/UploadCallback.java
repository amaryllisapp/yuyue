package com.ps.lc.net.callback;

import com.ps.lc.net.service.BaseResponse;

/**
 * Created by zhangwulin on 2017/1/10.
 * Email:zhangwulin@feitaikeji.com
 * 有data返回的
 */

public interface UploadCallback<R extends BaseResponse> extends NetWorkCallback {
    /**
     * 开始
     */
    void onUploadStart();

    /**
     * 结束
     */
    void onUploadCompleted();

    /**
     * 成功
     */
    void onUploadSuccess(R r);

    /**
     * 请求失败
     *
     * @param errorMsg 失败信息
     * @param code     失败码
     */
    void onUploadFailure(String errorMsg, String code);

    /**
     * 进度,默认在io线程
     */
    void onUpdateProgress(long total, long progress);
}
