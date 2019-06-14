package com.ps.yuyue;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lc.framework.router.share.ShareIntentManager;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.test_show)
    TextView mTestShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTestShow.setText("你好");
        mTestShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareIntentManager.intentToMainActivity();
                finish();
            }
        });
    }
}
