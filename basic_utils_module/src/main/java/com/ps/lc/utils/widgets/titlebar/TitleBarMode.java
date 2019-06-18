package com.ps.lc.utils.widgets.titlebar;

import android.support.annotation.IntDef;

/**
 * 类名：com.ps.lc.utils.widgets.titlebar
 * 描述：标题类型
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/17 17:14
 */
@IntDef({
        TitleBarMode.LIGHT,       // 白天
        TitleBarMode.NIGHT,       // 黑夜
        TitleBarMode.TRANS,       // 透明

})
public @interface TitleBarMode {
    /**
     * 无标题栏
     */
    int LIGHT = 0;
    /**
     * 仅存在返回按钮的标题栏
     */
    int NIGHT = 1;
    /**
     * 包含返回按钮和标题的标题栏
     */
    int TRANS = 2;
}
