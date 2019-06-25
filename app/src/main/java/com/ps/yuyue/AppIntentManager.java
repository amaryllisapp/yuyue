package com.ps.yuyue;

import com.lc.framework.router.BaseIntentManager;

import static com.ps.yuyue.AppRouterContants.APP_FRAGMENT;
import static com.ps.yuyue.AppRouterContants.APP_FRAGMENT2;

/**
 * 类名：com.ps.yuyue
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/24 16:57
 */
public class AppIntentManager extends BaseIntentManager {
    /**
     * 跳转到主界面
     */
    public static void intentToFragment1Activity() {
        intentRouter(APP_FRAGMENT).navigation();
    }

    /**
     * 跳转到主界面
     */
    public static void intentToFragment2Activity() {
        intentRouter(APP_FRAGMENT2).navigation();
    }
}
