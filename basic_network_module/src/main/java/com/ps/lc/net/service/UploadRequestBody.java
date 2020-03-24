package com.ps.lc.net.service;

import com.ps.lc.net.callback.UploadCallback;

import java.io.IOException;

import javax.annotation.Nonnull;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by zhangwulin on 2017/1/14.
 * Email:zhangwulin@feitaikeji.com
 */

public class UploadRequestBody extends RequestBody {
    private RequestBody mRequestBody;
    private UploadCallback mUploadCallback;

    public UploadRequestBody(@Nonnull RequestBody requestBody, @Nonnull UploadCallback uploadCallback) {
        mRequestBody = requestBody;
        mUploadCallback = uploadCallback;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        BufferedSink bufferedSink;

        CountingSink countingSink = new CountingSink(sink);
        bufferedSink = Okio.buffer(countingSink);

        mRequestBody.writeTo(bufferedSink);

        bufferedSink.flush();
    }

    protected final class CountingSink extends ForwardingSink {

        private long bytesWritten = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);

            bytesWritten += byteCount;
            if (null != mUploadCallback) {
                mUploadCallback.onUpdateProgress(contentLength(), bytesWritten);
            }
        }
    }


}
