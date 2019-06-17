package com.ps.lc.utils.widgets.titlebar.style;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.ps.lc.utils.R;

/**
 * 类名：TitleBarLightStyle
 * 描述：默认日间主题样式实现（布局属性：app:bar_style="light"）
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/17 17:36
 */
public class TitleBarLightStyle extends BaseTitleBarStyle {

    public TitleBarLightStyle(Context context) {
        super(context);
    }

    @Override
    public Drawable getBackground() {
        return new ColorDrawable(0xFFFFFFFF);
    }

    @Override
    public Drawable getBackIcon() {
        return getDrawable(R.mipmap.bar_icon_back_black);
    }

    @Override
    public int getLeftColor() {
        return 0xFF666666;
    }

    @Override
    public int getTitleColor() {
        return 0xFF222222;
    }

    @Override
    public int getRightColor() {
        return 0xFFA4A4A4;
    }

    @Override
    public boolean isLineVisible() {
        return true;
    }

    @Override
    public Drawable getLineDrawable() {
        return new ColorDrawable(0xFFECECEC);
    }

    @Override
    public int getLineSize() {
        return 1;
    }

    @Override
    public Drawable getLeftBackground() {
        return getDrawable(R.drawable.bar_selector_titlebar_white);
    }

    @Override
    public Drawable getRightBackground() {
        return getDrawable(R.drawable.bar_selector_titlebar_white);
    }
}