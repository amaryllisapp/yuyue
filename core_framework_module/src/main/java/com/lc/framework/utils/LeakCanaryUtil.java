package com.lc.framework.utils;

import android.app.Activity;
import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * LeakCanary工具类
 *
 * @author lizhenke
 * @date 2019/1/8 10:46
 */
public class LeakCanaryUtil {

    private static RefWatcher refWatcher;

    /**
     * 获取监听器
     * @return
     */
    public static RefWatcher getRefWatcher() {
        return refWatcher;
    }

    /**
     * 安装LeakCanary
     *
     * @param application application
     */
    public static void setupLeakCanary(Application application) {
        //LeakCanary
        if (!LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            refWatcher = LeakCanary.install(application);
        }
    }

    /**
     * 对Activity进行监听
     *
     * @param activity 监听的界面
     */
    public static void watchActivity(Activity activity) {
        RefWatcher refWatcher = LeakCanaryUtil.getRefWatcher();
        if (refWatcher != null) {
            refWatcher.watch(activity);
        }
    }
}
