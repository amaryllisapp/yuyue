package com.ps.lc.net.subscriber;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.ps.lc.net.NetworkCode;
import com.ps.lc.net.callback.DownloadCallback;

import java.io.File;

/**
 * Created by zhangwulin on 2017/1/10.
 * Email:zhangwulin@feitaikeji.com
 */

public class DownloadSubscriber extends BaseSubscriber<File> {
    private DownloadCallback mCallback;
    private static final int MSG_PROGRESS = 0x100;
    private static final int MSG_SUCCESS = 0x101;
    private static final int MSG_ERROR = 0x102;
    private static final String KEY_ALL_LENGTH = "KEY_ALL_LENGTH";
    private static final String KEY_DOWNLOAD_SIZE = "KEY_DOWNLOAD_SIZE";
    private static final String KEY_MSG = "KEY_MSG";


    private Handler mMainHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {//因ui线程有个队列问题才这样先
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_PROGRESS:
                    if (null != msg.getData() && null != mCallback) {
                        Bundle bundle = msg.getData();
                        mCallback.onDownloadProgress(bundle.getLong(KEY_ALL_LENGTH, 0), bundle.getLong(KEY_DOWNLOAD_SIZE, 0));
                    }
                    break;
                case MSG_SUCCESS:
                    if (null != mCallback) {
                        if (msg.obj instanceof File) {
                            mCallback.onDownloadCompleted((File) msg.obj, "success");
                        } else {
                            mCallback.onDownloadCompleted(null, NetworkCode.getCodeDesc(NetworkCode.RESPONSE_E30000));
                        }
                    }
                    break;
                case MSG_ERROR:
                    if (null != msg.getData() && null != mCallback) {
                        Bundle bundle = msg.getData();
                        mCallback.onDownloadCompleted(null, bundle.getString(KEY_MSG, ""));
                    }
                    break;
            }
            return false;
        }
    });

    public DownloadSubscriber(DownloadCallback callback) {
        mCallback = callback;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (null != mCallback) {
            mCallback.onDownloadStart();
        }
    }

    public void setProgress(long allLength, long fileSizeDownloaded) {
        Bundle bundle = new Bundle();
        bundle.putLong(KEY_ALL_LENGTH, allLength);
        bundle.putLong(KEY_DOWNLOAD_SIZE, fileSizeDownloaded);
        Message msg = mMainHandler.obtainMessage(MSG_PROGRESS);
        msg.setData(bundle);
        msg.sendToTarget();
    }

    @Override
    protected void handleError(Throwable e) {

    }

    @Override
    protected void showError(String code, String msg) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_MSG, msg);
        Message message = mMainHandler.obtainMessage(MSG_ERROR);
        message.setData(bundle);
        message.sendToTarget();
    }

    @Override
    protected void showErrorWithData(String code, String msg, Object object) {
        // unused
    }


    @Override
    protected void handleNext(File file) {
        mMainHandler.obtainMessage(MSG_SUCCESS, file).sendToTarget();
    }

    @Override
    protected void completed() {
    }
}
