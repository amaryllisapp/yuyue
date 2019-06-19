package com.ps.lc.widget.emptyview;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 情感图类型定义
 *
 * @author 36077 - liucheng@xhg.com
 * @date 2018/11/26 10:24
 */
@IntDef({
        EmptyViewType.TAG_NO_CONTENT,                // 无内容标记
        EmptyViewType.TAG_NETWOEK_ERROR,             // 无网络标记
        EmptyViewType.TAG_CONNECTION_ERROR,          //  网络连接异常标记
        EmptyViewType.TAG_LOCATION_ERROR,            // 定位失败标记
        EmptyViewType.TAG_CUSTOM_ERROR,              // 自定义标记
})
@Retention(RetentionPolicy.SOURCE)
public @interface EmptyViewType {
    /**
     * 无内容标记， 点击带下划线的按钮
     */
    int TAG_NO_CONTENT = 0x1001;
    /**
     * 无网络标记，点击屏幕
     */
    int TAG_NETWOEK_ERROR = 0x1002;
    /**
     * 网络连接异常标记，点击屏幕
     */
    int TAG_CONNECTION_ERROR = 0x1003;
    /**
     * 定位失败标记， 点击屏幕
     */
    int TAG_LOCATION_ERROR = 0x1004;
    /**
     * 自定义标记
     */
    int TAG_CUSTOM_ERROR = 0x1005;
}
