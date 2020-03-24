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
        TitleBarType.NON,               // 无标题栏
        TitleBarType.LEFT_ONLY,         // 仅存在返回按钮的标题栏
        TitleBarType.LEFT_CENTER,       // 包含返回按钮和标题的标题栏
        TitleBarType.RIGHT_IMAGE,       // 包含返回、标题、右键（图标）的标题栏
        TitleBarType.RIGHT_STRING,      // 包含返回、标题、右键（文字）的标题栏
        TitleBarType.RIGHT_CUSTOMIZE,   // 包含返回、标题、右键（自定义）的标题栏
        TitleBarType.MULTI_CUSTOMIZE,   // 完全自定义的标题栏
})
public @interface TitleBarType {
    /**
     * 无标题栏
     */
    int NON = 0;
    /**
     * 仅存在返回按钮的标题栏
     */
    int LEFT_ONLY = 1;
    /**
     * 包含返回按钮和标题的标题栏
     */
    int LEFT_CENTER = 2;
    /**
     * 包含返回、标题、右键（图标）的标题栏
     */
    int RIGHT_IMAGE = 3;
    /**
     * 包含返回、标题、右键（文字）的标题栏
     */
    int RIGHT_STRING = 4;
    /**
     * 包含返回、标题、右键（自定义）的标题栏
     */
    int RIGHT_CUSTOMIZE = 5;
    /**
     * 完全自定义的标题栏
     */
    int MULTI_CUSTOMIZE = 6;
}
