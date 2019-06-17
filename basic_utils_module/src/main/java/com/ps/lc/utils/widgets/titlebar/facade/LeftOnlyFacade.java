package com.ps.lc.utils.widgets.titlebar.facade;

import android.view.View;

import com.ps.lc.utils.R;

/**
 * 类名：com.ps.lc.utils.widgets.titlebar.facade
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/17 19:04
 */
public class LeftOnlyFacade extends BaseFacade {

    public LeftOnlyFacade(int type) {
        super(type);
    }

    /**
     * 子类执行
     */
    @Override
    protected void execute() {
        mTitleBar.setLeftTitle(R.string.title_bar_back);
        mTitleBar.setLeftIcon(R.drawable.icon_common_back);
        mTitleBar.getTitleView().setVisibility(View.GONE);
    }
}
