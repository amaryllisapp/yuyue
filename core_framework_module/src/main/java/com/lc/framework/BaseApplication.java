package com.lc.framework;

import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ps.lc.utils.UtilsHelper;

/**
 * 类名：com.lc.framework
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/14 17:55
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
        UtilsHelper.init(this, BuildConfig.DEBUG);
    }
}
