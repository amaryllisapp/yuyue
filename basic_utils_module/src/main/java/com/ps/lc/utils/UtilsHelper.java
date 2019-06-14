package com.ps.lc.utils;

import android.app.Application;

import com.ps.lc.utils.log.LogHelper;
import com.ps.lc.utils.shared.SharedManager;

/**
 * 类名：UtilsHelper
 * 描述：工具类实例化
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/5/24 17:30
 */
public class UtilsHelper {

    public static void init(Application application, boolean DEBUG) {
        AppContextUtil.init(application);
        LogHelper.init(DEBUG, application.getPackageName());
        SharedManager.init(application);
        HardwareUtil.init(application);
    }
}
