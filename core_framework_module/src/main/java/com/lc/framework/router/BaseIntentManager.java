package com.lc.framework.router;

import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 类名：com.lc.framework.router
 * 描述：跳转管理器，需要做模块跳转的内容写入到该类，否则各自类继承该类实现即可。
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/14 17:10
 */
public abstract class BaseIntentManager {
    /**
     * @param path {@link BaseRouterConstants}
     */
    public static Postcard intentRouter(@NonNull String path) {
        return ARouter.getInstance().build(path);
    }
}
