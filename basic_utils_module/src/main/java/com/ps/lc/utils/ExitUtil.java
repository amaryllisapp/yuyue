package com.ps.lc.utils;

import android.content.Context;

/**
 * Created by zhangwulin on 2017/5/31.
 * Email:zhangwulin@feitaikeji.com
 */

public class ExitUtil {
    /**
     * 退出APP
     */
    private static boolean mIsExit = false;
    public static final int WAIT_TIME = 2000;

    public static void exitApp(Context context, Runnable runnable) {
        if (mIsExit == false) {
            mIsExit = true;
            ToastUtil.showToast(context, R.string.exit);
            ThreadManager.postDelayed(ThreadManager.THREAD_WORK, new Runnable() {
                @Override
                public void run() {
                    mIsExit = false;
                }
            }, WAIT_TIME);
        } else {
            exit(context, runnable);
        }
    }

    public static void exitApp(Context context) {
        exitApp(context, null);
    }

    public static void exit(Context context, Runnable runnable) {
        if (runnable != null) {
            runnable.run();
        }
        ActivityUtil.AppExit(context);
    }

    public static void exit(Runnable runnable) {
        exit(null, runnable);
    }
}
