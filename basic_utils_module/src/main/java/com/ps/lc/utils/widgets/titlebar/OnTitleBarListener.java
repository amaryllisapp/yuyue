package com.ps.lc.utils.widgets.titlebar;

import android.view.View;

/**
 *
 * 类名：OnTitleBarListener
 * 描述：标题栏点击监听接口
 *
 * @author liucheng - liucheng@xhg.com
 *
 * @date 2019/6/17 17:40
 */
public interface OnTitleBarListener {

    /**
     * 左项被点击
     *
     * @param v     被点击的左项View
     */
    void onLeftClick(View v);

    /**
     * 标题被点击
     *
     * @param v     被点击的标题View
     */
    void onTitleClick(View v);

    /**
     * 右项被点击
     *
     * @param v     被点击的右项View
     */
    void onRightClick(View v);
}