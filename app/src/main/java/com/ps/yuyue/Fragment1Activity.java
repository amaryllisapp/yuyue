package com.ps.yuyue;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lc.framework.core.activity.BaseCommFragmentActivity;
import com.lc.framework.core.activity.fragment.BaseAbsFragment;
import com.ps.yuyue.fragment.Fragment1;

import static com.ps.yuyue.AppRouterContants.APP_FRAGMENT;

/**
 * 类名：com.ps.yuyue
 * 描述：测试列表页面
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/14 16:58
 */
@Route(path = APP_FRAGMENT)
public class Fragment1Activity extends BaseCommFragmentActivity {

    @Override
    protected String getTitleName() {
        return "fragment";
    }

    @Override
    protected BaseAbsFragment fragment() {
        return Fragment1.create();
    }

    @Override
    protected void initView(Bundle savedInstanceState, LinearLayout containerLay) {

    }
}
