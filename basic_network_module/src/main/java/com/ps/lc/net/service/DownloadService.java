package com.ps.lc.net.service;

import com.ps.lc.net.NetworkManager;
import com.ps.lc.net.callback.DownloadCallback;
import com.ps.lc.net.callback.NetWorkCallback;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by zhangwulin on 2017/1/10.
 * Email:zhangwulin@feitaikeji.com
 */

public abstract class DownloadService extends BaseService<IDownloadService, ResponseBody> {


    @Override
    public Observable<ResponseBody> getObservable(IDownloadService service) {
        return service.download(getUrl());
    }

    /**
     * 下载
     *
     * @param callback
     */
    public void download(DownloadCallback callback) {
        execute(callback);
    }

    /**
     * 连网
     *
     * @param callBack
     */
    @Override
    public void execute(NetWorkCallback callBack) {
        if (callBack instanceof DownloadCallback) {
            mSubscriber = NetworkManager.getInstance().download(this, (DownloadCallback) callBack);
        } else {
            throw new IllegalArgumentException("callback type is error");
        }
    }

    /**
     * 取消连网
     */
    @Override
    public void cancel() {
        if (null != mSubscriber) {
            mSubscriber.destroy();
            mSubscriber = null;
        }
    }


    /**
     * 下载地址
     *
     * @return
     */
    public abstract String getUrl();

    /**
     * 文件目录
     *
     * @return
     */
    public abstract String getDirPath();

    /**
     * 文件名，不设置，默认为url上的文件名
     */
    public abstract String getFileName();

    /**
     * 是否在下载前不删除存在的文件，
     *
     * @return 默认false要删除存在的文件
     */
    public abstract boolean isNotDelExistsFile();

}
