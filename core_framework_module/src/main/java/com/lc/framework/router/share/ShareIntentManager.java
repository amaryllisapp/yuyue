package com.lc.framework.router.share;

import com.lc.framework.router.BaseIntentManager;

/**
 * 类名：com.lc.framework.router.share
 * 描述：需要在各个模块中进行调用的跳转，在该页面进行调用
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/14 17:24
 */
public class ShareIntentManager extends BaseIntentManager {
    /**
     * 跳转到主界面
     */
    public static void intentToMainActivity() {
        intentRouter(ShareRouterContants.PUBLIC_MAIN).navigation();
    }

    /**
     * 跳转到Kotlin主界面
     */
    public static void intentToKotlinMainActivity() {
        intentRouter(ShareRouterContants.PUBLIC_KOTLIN_MAIN).navigation();
    }
}
