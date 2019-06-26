
package com.ps.lc.net.fun;


import android.util.Log;

import com.ps.lc.net.service.BaseService;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.Nonnull;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by zhangwulin on 2016/11/3.
 * Eamil zhangwulin@feitaikeji.com
 */

public class FuncRetry implements Func1<Observable<? extends Throwable>, Observable<?>> {

    private BaseService mService;

    public FuncRetry(@Nonnull BaseService service) {
        mService = service;
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> observable) {
        return observable
                .zipWith(Observable.range(1, mService.getRetryCount() + 1),
                        new Func2<Throwable, Integer, Wrapper>() {
                            @Override
                            public Wrapper call(Throwable throwable, Integer integer) {
                                return new Wrapper(throwable, integer);
                            }
                        })
                .flatMap(new Func1<Wrapper, Observable<?>>() {
                    @Override
                    public Observable<?> call(Wrapper wrapper) {
                        if ((wrapper.throwable instanceof ConnectException
                                || wrapper.throwable instanceof SocketTimeoutException
                                || wrapper.throwable instanceof TimeoutException)
                                && wrapper.index < mService.getRetryCount() + 1) {
                            //如果超出重试次数也抛出错误，否则默认是会进入onCompleted
                            Log.e("Zenfer", "Observable.timer excute");
                            return Observable.timer(mService.getRetryDelay() + (wrapper.index - 1) * mService.getRetryIncreaseDelay(), TimeUnit.MILLISECONDS);

                        }
                        Log.e("Zenfer", "Observable.error excute");
                        return Observable.error(wrapper.throwable);
                    }
                });
    }

    private class Wrapper {
        private int index;
        private Throwable throwable;

        public Wrapper(Throwable throwable, int index) {
            this.index = index;
            this.throwable = throwable;
        }
    }
}
