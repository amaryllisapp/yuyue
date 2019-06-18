package com.ps.yuyue;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lc.framework.core.activity.CommonAbsActivity;
import com.lc.framework.router.share.ShareIntentManager;
import com.ps.lc.utils.widgets.titlebar.TitleBarType;

import butterknife.BindView;


public class MainActivity extends CommonAbsActivity {

    @BindView(R.id.test_show)
    TextView mTestShow;

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTitleName() {
        return "主界面";
    }

    /**
     * 标题栏
     */
    @Override
    public void initTitleView() {
        mTitleBarManager.type(TitleBarType.RIGHT_STRING).apply();
    }

    @Override
    protected void initStatusBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true, 0.2f)
                .barColor(R.color.transparent)
                .titleBar(mTitleBarManager.getTitleBar()).init();
    }

    @Override
    protected void initView(Bundle savedInstanceState, LinearLayout containerLay) {
//        setTitleBarVisible(false);
        mTestShow.setText("你好");
        mTestShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareIntentManager.intentToMainActivity();
            }
        });
    }
}
