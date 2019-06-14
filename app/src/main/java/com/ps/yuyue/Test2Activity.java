package com.ps.yuyue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lc.framework.router.share.ShareRouterContants.PUBLIC_MAIN;

/**
 * 类名：com.ps.yuyue
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/14 16:58
 */
@Route(path = PUBLIC_MAIN)
public class Test2Activity extends AppCompatActivity {
    @BindView(R.id.test_show)
    TextView mTestShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTestShow.setText("你好,est");
    }
}
