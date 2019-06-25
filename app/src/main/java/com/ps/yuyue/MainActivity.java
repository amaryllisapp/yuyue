package com.ps.yuyue;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.lc.framework.core.activity.CommonAbsActivity;
import com.lc.framework.router.share.ShareIntentManager;
import com.ps.lc.utils.widgets.titlebar.TitleBarType;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends CommonAbsActivity {

    @BindView(R.id.test_show)
    Button mTestShow;
    @BindView(R.id.fragment_show)
    Button mFragmentShow;

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
                // 解决软键盘与底部输入框冲突问题
                .keyboardEnable(true)
                .titleBar(mTitleBarManager.getTitleBar()).init();
    }

    @Override
    protected void initView(Bundle savedInstanceState, LinearLayout containerLay) {

    }

    @OnClick({R.id.test_show, R.id.fragment_show,R.id.fragment_tab_show})
    void onActionEvent(View view) {
        switch (view.getId()) {
            case R.id.test_show:
                ShareIntentManager.intentToMainActivity();
                break;
            case R.id.fragment_show:
                AppIntentManager.intentToFragment1Activity();
                break;
            case R.id.fragment_tab_show:
                AppIntentManager.intentToFragment2Activity();
                break;
            default:
        }
    }
}
