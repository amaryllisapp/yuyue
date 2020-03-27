package com.ps.yuyue.router;

import com.lc.framework.router.BaseIntentManager;

import static com.ps.yuyue.router.AppRouterContants.APP_FRAGMENT;
import static com.ps.yuyue.router.AppRouterContants.APP_FRAGMENT2;
import static com.ps.yuyue.router.AppRouterContants.APP_MAIN;

/**
 * 类名：com.ps.yuyue
 * 描述：页面跳转路由规则
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

    /**
     * 跳转到主界面
     */
    public static void intentToMainActivity() {
        intentRouter(APP_MAIN).navigation();
    }
}
