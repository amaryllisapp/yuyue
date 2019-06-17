package com.ps.lc.utils.widgets.titlebar.style;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;


/**
 *
 * 类名：BaseTitleBarStyle
 * 描述：默认主题样式基类
 *
 * @author liucheng - liucheng@xhg.com
 *
 * @date 2019/6/17 17:36
 */
public abstract class BaseTitleBarStyle implements ITitleBarStyle {

    private Context mContext;

    public BaseTitleBarStyle(Context context) {
        mContext = context.getApplicationContext();
    }

    protected Context getContext() {
        return mContext;
    }

    protected Drawable getDrawable(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getContext().getResources().getDrawable(id, null);
        } else {
            return getContext().getResources().getDrawable(id);
        }
    }

    public int getColor(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getContext().getResources().getColor(id, null);
        } else {
            return getContext().getResources().getColor(id);
        }
    }

    /**
     * dp转px
     */
    protected int dp2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * sp转px
     */
    protected int sp2px(float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    @Override
    public int getTitleBarHeight() {
        return dp2px(0);
    }

    @Override
    public float getLeftSize() {
        return sp2px(14);
    }

    @Override
    public float getTitleSize() {
        return sp2px(16);
    }

    @Override
    public float getRightSize() {
        return sp2px(14);
    }
}