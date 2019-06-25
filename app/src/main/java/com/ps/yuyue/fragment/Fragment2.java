package com.ps.yuyue.fragment;

import android.os.Bundle;
import android.view.ViewGroup;

import com.lc.framework.core.activity.fragment.BaseAbsFragment;
import com.ps.yuyue.R;

/**
 * 类名：com.ps.yuyue.fragment
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/24 17:02
 */
public class Fragment2 extends BaseAbsFragment {
    public static Fragment2 create(){
        return new Fragment2();
    }

    @Override
    protected int layout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState, ViewGroup containerLay) {

    }
}
