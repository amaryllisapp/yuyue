package com.ps.lc.utils;

import android.content.Context;

/**
 * Created by substring on 2016/8/1.
 * Email：zhangxuan@feitaikeji.com
 */
public class AppContextUtil {

    private static Context sAppContext;

    /*package*/
    public static void init(Context context) {
        sAppContext = context;
    }

    public static Context getAppContext() {
        return sAppContext;
    }

    public static Object getSystemService(String name) {
        if (null == name) {
            return null;
        }
        return sAppContext.getSystemService(name);
    }
}
