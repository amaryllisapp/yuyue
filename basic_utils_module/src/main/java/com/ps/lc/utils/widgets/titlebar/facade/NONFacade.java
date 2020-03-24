package com.ps.lc.utils.widgets.titlebar.facade;

import android.view.View;

/**
 * 类名：com.ps.lc.utils.widgets.titlebar.facade
 * 描述：无
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/17 18:07
 */
public class NONFacade extends BaseFacade {

    public NONFacade(int type) {
        super(type);
    }

    @Override
    protected void execute() {
        mTitleBar.setVisibility(View.GONE);
    }
}
