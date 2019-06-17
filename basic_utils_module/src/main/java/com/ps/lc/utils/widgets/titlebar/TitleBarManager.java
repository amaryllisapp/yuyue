package com.ps.lc.utils.widgets.titlebar;

import android.support.annotation.NonNull;

import com.ps.lc.utils.widgets.titlebar.facade.BaseFacade;

/**
 * 类名：com.ps.lc.utils.widgets.titlebar
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/17 16:55
 */
public class TitleBarManager {

    private TitleBar mTitleBar;

    private OnTitleBarListener mListener;

    private BaseFacade baseFacade;

    private @TitleBarType
    int mType;

    public @NonNull
    TitleBarManager with(@NonNull TitleBar titleBar) {
        this.mTitleBar = titleBar;
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

    public void apply() {
        BaseFacade baseFacade = BaseFacade.newInstance(mType).with(mTitleBar);
        baseFacade.apply();
    }

    public void onDestroy(){
        baseFacade.destroy();
    }
}
