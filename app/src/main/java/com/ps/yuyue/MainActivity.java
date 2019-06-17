package com.ps.yuyue;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lc.framework.core.activity.CommonAbsActivity;
import com.lc.framework.router.share.ShareIntentManager;

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

    @Override
    protected void initView(Bundle savedInstanceState, LinearLayout containerLay) {
        setTitleBarVisible(false);
        mTestShow.setText("你好");
        mTestShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareIntentManager.intentToMainActivity();
            }
        });
    }
}
