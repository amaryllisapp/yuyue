package com.ps.lc.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ps.lc.utils.log.LogHelper;

import java.lang.reflect.Method;

/**
 * Created by DongJianYu on 2017/2/22.
 * Email: dongjianyu@feitaikeji.com
 */

public class DimensionUtil {
    /**
     * 获取屏幕原始尺寸高度，包括虚拟功能键高度,状态栏、标题栏。
     * 即整块屏幕的分辨率高度
     *
     * @param context
     * @return 如果发生错误，尺寸有可能返回0
     */
    public static int getDeviceHeight(Context context) {
        int height = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Class c;
        try {
            c = Class.forName("android.view.Display");
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            height = displayMetrics.heightPixels;
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        return height;
    }

    /**
     * 设备宽度的分辨率
     *
     * @param context
     * @return 如果发生错误，尺寸有可能返回0
     */
    public static int getDeviceWidth(Context context) {
        int width = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            width = displayMetrics.widthPixels;
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        return width;
    }

    /**
     * 获取 虚拟按键的高度
     *
     * @param context
     * @return
     */
    public static int getBottomStatusHeight(Context context) {
        int totalHeight = getDeviceHeight(context);

        int contentHeight = getScreenHeight(context);

        return totalHeight - contentHeight;
    }

    /**
     * 获取导航栏高度
     *
     * @param context
     * @return
     */
    public static int getNavigationHeight(Context context) {
        int resourceId;
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return context.getResources().getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    /**
     * 标题栏高度
     *
     * @return
     */
    public static int getTitleHeight(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        return statusHeight;
    }


    /**
     * 获得屏幕高度，不包括虚拟按键，但包括状态栏
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获取屏幕可显示区域的高度,不包括导航栏.分api级别
     *
     * @param context
     * @return
     */
    public static int getHeightCanDisplay(Context context) {
        int deviceHeight = getScreenHeight(context);
        int canDisplayHeight = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            canDisplayHeight = deviceHeight;
        } else {
            canDisplayHeight = deviceHeight - getStatusHeight(context);
        }
        return canDisplayHeight;
    }

    public static float getScreenRatio(Context context) {
        int heightCanDisplay = getHeightCanDisplay(context);
        int deviceWidth = getDeviceWidth(context);
        return (float) deviceWidth / heightCanDisplay;
    }

    /**
     * 获取view 未显示时的宽高
     *
     * @param view view
     */
    public static int[] unDisplayViewSize(View view) {
        int size[] = new int[2];
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        size[0] = view.getMeasuredWidth();
        size[1] = view.getMeasuredHeight();
        return size;
    }

    public static void print(Activity context) {
        LogHelper.i("分辨率高度:" + getDeviceHeight(context));
        LogHelper.i("分辨率宽度:" + getDeviceWidth(context));
        LogHelper.i("虚拟按键的高度:" + getBottomStatusHeight(context));
        LogHelper.i("标题栏高度:" + getTitleHeight(context));
        LogHelper.i("状态栏的高度:" + getStatusHeight(context));
        LogHelper.i("屏幕高度，不包括虚拟按键，但包括状态栏:" + getScreenHeight(context));
        LogHelper.i("屏幕可显示区域的高度,不包括导航栏.分api级别:" + getHeightCanDisplay(context));
    }
}

