package com.ps.yuyue;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lc.framework.core.activity.BaseCommFragmentActivity;
import com.lc.framework.core.activity.TabFragmentActivity;
import com.lc.framework.core.activity.fragment.BaseAbsFragment;
import com.lc.framework.core.bean.BaseFragmentBean;
import com.ps.yuyue.fragment.Fragment1;
import com.ps.yuyue.fragment.Fragment2;
import com.ps.yuyue.fragment.Fragment3;

import java.util.ArrayList;
import java.util.List;

import static com.ps.yuyue.AppRouterContants.APP_FRAGMENT;
import static com.ps.yuyue.AppRouterContants.APP_FRAGMENT2;

/**
 * 类名：com.ps.yuyue
 * 描述：测试列表页面
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/14 16:58
 */
@Route(path = APP_FRAGMENT2)
public class Fragment2Activity extends TabFragmentActivity {

    @Override
    protected String getTitleName() {
        return "fragment";
    }

    @NonNull
    @Override
    protected List<BaseFragmentBean> loadFragmentBeans() {
        List<BaseFragmentBean> list = new ArrayList<>();
        list.add(new BaseFragmentBean("fragment1", Fragment1.create()));
        list.add(new BaseFragmentBean("fragment2", Fragment2.create()));
        list.add(new BaseFragmentBean("fragment3", Fragment3.create()));
        return list;
    }

    @Override
    protected void initView(Bundle savedInstanceState, LinearLayout containerLay) {
        super.initView(savedInstanceState, containerLay);
    }
}
