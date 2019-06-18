package com.lc.framework.core.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.lc.framework.R;

import butterknife.Unbinder;

/**
 * 类名：com.lc.framework.core.activity
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/18 11:14
 */
public abstract class ListAbsActivity extends BaseAbsActivity{

    private Unbinder mButterKnife;

    @Override
    protected void initParentWindowFeature() {}

    @Override
    protected int getRootLayoutId() {
        return R.layout.acty_list_parent;
    }

    /**
     * 获取布局ID
     *
     * @return
     */
    public abstract int layoutId();

    /**
     * 获取标题名称
     *
     * @return
     */
    protected abstract String getTitleName();

    protected abstract void initView(Bundle savedInstanceState, LinearLayout containerLay);

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;

        // 初始化根节点布局视图
        initParentView();
        // 加载内容布局到内容容器
        attchToContainerView();
        initTitleBar();
        initStatusBar();

        // 绑定View
        mButterKnife = mBaseAbsHelper.bindButterKnife(this);
        initView(savedInstanceState, mContainerLay);
    }
}
