package com.ps.lc.utils.widgets.titlebar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.ps.lc.utils.widgets.titlebar.facade.BaseFacade;
import com.ps.lc.utils.widgets.titlebar.style.BaseTitleBarStyle;
import com.ps.lc.utils.widgets.titlebar.style.TitleBarLightStyle;
import com.ps.lc.utils.widgets.titlebar.style.TitleBarNightStyle;
import com.ps.lc.utils.widgets.titlebar.style.TitleBarTransparentStyle;

/**
 * 类名：com.ps.lc.utils.widgets.titlebar
 * 描述：标题栏管理器
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/17 16:55
 */
public class TitleBarManager {

    private TitleBar mTitleBar;

    private Context mContext;

    private OnTitleBarListener mListener;

    private BaseFacade mBaseFacade;

    private BaseTitleBarStyle mTitleBarStyle;

    private @TitleBarType
    int mType;
    public TitleBarManager with(@NonNull ViewGroup view, ViewGroup.LayoutParams params) {
        mTitleBar = new TitleBar(mContext = view.getContext());
        view.addView(mTitleBar, params);
        mTitleBarStyle = new TitleBarLightStyle(mContext);
        return this;
    }

    public @NonNull
    TitleBarManager listener(OnTitleBarListener listener) {
        this.mListener = listener;
        return this;
    }

    public @NonNull
    TitleBarManager type(@NonNull @TitleBarType int type) {
        mType = type;
        return this;
    }

    public @NonNull
    TitleBarManager mode(@NonNull @TitleBarMode int mode) {
        switch (mode) {
            case TitleBarMode.LIGHT:
                mTitleBarStyle = new TitleBarLightStyle(mContext);
                break;
            case TitleBarMode.NIGHT:
                mTitleBarStyle = new TitleBarNightStyle(mContext);
                break;
            case TitleBarMode.TRANS:
                mTitleBarStyle = new TitleBarTransparentStyle(mContext);
                break;
            default:
        }
        return this;
    }

    public void apply() {
        if (mListener != null) {
            mTitleBar.setOnTitleBarListener(mListener);
        }
        if (mTitleBarStyle != null) {
            TitleBar.initStyle(mTitleBarStyle);
        }
        mBaseFacade = BaseFacade.newInstance(mType).with(mTitleBar);
        mBaseFacade.apply();
    }

    public void setTitle(String title){
        mTitleBar.setTitle(title);
    }

    public TitleBar getTitleBar(){
        return mTitleBar;
    }

    public void onDestroy() {
        if (mBaseFacade != null) {
            mBaseFacade.destroy();
            mBaseFacade = null;
        }
        if (mContext != null) {
            mContext = null;
        }
    }
}
