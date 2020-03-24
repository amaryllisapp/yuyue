package com.ps.lc.net.fun;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.ps.lc.net.Utils;
import com.ps.lc.net.service.DownloadService;
import com.ps.lc.net.subscriber.DownloadSubscriber;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import javax.annotation.Nonnull;

import okhttp3.ResponseBody;
import rx.functions.Func1;

/**
 * Created by zhangwulin on 2017/1/10.
 * Email:zhangwulin@feitaikeji.com
 */

public class FuncDownload implements Func1<ResponseBody, File> {
    private DownloadService mService;
    private DownloadSubscriber mDownloadSubscriber;

    public FuncDownload(@Nonnull DownloadService service, @NonNull DownloadSubscriber downloadSubscriber) {
        mService = service;
        mDownloadSubscriber = downloadSubscriber;
    }

    @Override
    public File call(ResponseBody responseBody) {
        String fileName = mService.getFileName();
        if (TextUtils.isEmpty(fileName)) {
            if (TextUtils.isEmpty(mService.getUrl())) {
                throw new IllegalStateException("not url");
            } else {
                String url = mService.getUrl();
                int i = url.lastIndexOf('/');
                if (i > 7 && i < url.length() - 1) {
                    fileName = url.substring(i + 1);
                } else {
                    throw new IllegalStateException("not fileName");
                }
            }
        }
        if (TextUtils.isEmpty(fileName)) {
            throw new IllegalStateException("not fileName");
        }
        File f = new File(mService.getDirPath(), fileName);
        if (!mService.isNotDelExistsFile() && f.exists()) {
            f.delete();
        }
        if (writeFile(responseBody, f)) {
            return f.exists() ? f : null;
        }
        return null;
    }

    private boolean writeFile(ResponseBody responseBody, File file) {
        if (null != file.getParentFile() && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        long allLength = responseBody.contentLength();
        RandomAccessFile randomAccessFile = null;
        FileChannel channelOut = null;
        InputStream is = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rwd");
            channelOut = randomAccessFile.getChannel();
            MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE, 0, allLength);
            byte[] buffer = new byte[1024 * 8];
            is = responseBody.byteStream();
            long fileSizeDownloaded = 0;
            int len;
            long curTime = 0;
            while ((len = is.read(buffer)) != -1) {
                mappedBuffer.put(buffer, 0, len);
                fileSizeDownloaded += len;
                if (System.currentTimeMillis() - curTime > 100L) {//100毫秒更新一次
                    curTime = System.currentTimeMillis();
                    mDownloadSubscriber.setProgress(allLength, fileSizeDownloaded);
                }
            }
            mDownloadSubscriber.setProgress(allLength, fileSizeDownloaded);
            return true;
        } catch (Exception e) {
            Log.getStackTraceString(e);
            return false;
        } finally {
            Utils.closeQuietly(is);
            Utils.closeQuietly(channelOut);
            Utils.closeQuietly(randomAccessFile);
        }
    }
}
