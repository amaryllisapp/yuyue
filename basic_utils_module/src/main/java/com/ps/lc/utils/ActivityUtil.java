package com.ps.lc.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.view.View;

import java.util.Stack;

/**
 * Created by zhangwulin on 2017/12/5.
 * Email:zhangwulin@feitaikeji.com
 */

public class ActivityUtil {

    private static Stack<Activity> activityStack = new Stack<Activity>();

    public static void showActivity(Activity activity, Class obj, boolean closeActivity) {
        showActivity(activity, obj, closeActivity, false);
    }

    public static void showActivity(Activity activity, Class obj, boolean closeActivity, boolean withAlphaAnim) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(activity, obj);
        activity.startActivity(intent);
        if (closeActivity) {
            finishActivity(activity);
            activity.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        }
    }

    public static void showActivity(Activity activity, Intent intent, boolean closeActivity) {
        showActivity(activity, intent, closeActivity, false);
    }

    public static void showActivity(Activity activity, Intent intent, boolean closeActivity, boolean withAlphaAnim) {
        if (activity == null) {
            return;
        }
        activity.startActivity(intent);
        if (closeActivity) {
            finishActivity(activity);
            activity.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        }
    }


    public static void showTransAnimActivity(Activity activity, Class obj, Bundle bundle, View tansAnimView, String viewTag, boolean closeActivity) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(activity, obj);
        intent.putExtras(bundle);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH && tansAnimView != null && StringUtil.isNotEmpty(viewTag)) {
            activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity, tansAnimView, viewTag).toBundle());
        } else {
            activity.startActivity(intent);
        }
        if (closeActivity) {
            finishActivity(activity);
        }
    }

    public static void showActivityForResult(Activity activity, Class obj, boolean closeActivity, int requestCode) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(activity, obj);
        activity.startActivityForResult(intent, requestCode);
        if (closeActivity) {
            finishActivity(activity);
        }
    }

    public static void finishActivity(Activity activity, boolean withAnim) {
        if (activity == null) {
            return;
        }
        finishActivity(activity);
        if (!withAnim) {
            return;
        }
        activity.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    /**
     * 添加Activity到堆栈
     */
    public static void addActivity(Activity activity) {
        activityStack.push(activity);
    }

    public static void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        if (activityStack.isEmpty()) {
            return null;
        }
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public static void finishCurrentActivity() {
        if (activityStack.isEmpty()) {
            return;
        }
        Activity activity = activityStack.pop();
        activity.finish();
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        activityStack.remove(activity);
        if (!activity.isFinishing()) {
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                return;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 删除当前Activity以外的所有界面
     */
    public static void removeExceptByCurrentActivity(Class className) {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            if (activity.getClass().equals(className)) {
                continue;
            }
            finishActivity(activity);
        }
    }

    /**
     * 检测activity是否存在
     *
     * @param cls 要判断的activity
     * @return true has exist false no exist
     */
    public static boolean doCheckActivityIsExist(Class<?> cls) {
        if (cls == null || activityStack == null) {
            return false;
        }
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 退出应用程序
     */
    public static void AppExit(Context context) {
        try {
            finishAllActivity();
            if (context != null) {
                ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                if (manager != null) {
                    manager.killBackgroundProcesses(context.getPackageName());
                }
            }
            Process.killProcess(Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
