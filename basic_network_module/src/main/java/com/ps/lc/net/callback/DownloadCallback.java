package com.ps.lc.net.callback;

import android.support.annotation.Nullable;

import java.io.File;

/**
 * Created by zhangwulin on 2017/1/10.
 * Email:zhangwulin@feitaikeji.com
 */

public interface DownloadCallback extends NetWorkCallback {
    /**
     * 开始
     */
    void onDownloadStart();

    /**
     * 结束
     *
     * @param file     null 为失败
     * @param errorMsg 失败时返回
     */
    void onDownloadCompleted(@Nullable File file, String errorMsg);

    /**
     * 进度,默认在io线程
     */
    void onDownloadProgress(long total, long progress);
}
