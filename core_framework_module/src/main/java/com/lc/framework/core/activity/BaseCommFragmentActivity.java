package com.lc.framework.core.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lc.framework.R;
import com.lc.framework.core.activity.fragment.BaseAbsFragment;
import com.ps.lc.utils.ResourceUtil;
import com.ps.lc.utils.widgets.titlebar.OnTitleBarListener;
import com.ps.lc.utils.widgets.titlebar.TitleBarManager;
import com.ps.lc.utils.widgets.titlebar.TitleBarType;
import com.ps.lc.widget.emptyview.EmptyViewHelper;
import com.ps.lc.widget.emptyview.EmptyViewType;
import com.ps.lc.widget.emptyview.OnEmptyViewClickListener;

import butterknife.Unbinder;

/**
 * 类名：com.lc.framework.core.activity
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/24 16:27
 */
public abstract class BaseCommFragmentActivity extends BaseAbsActivity implements OnTitleBarListener, OnEmptyViewClickListener {
    protected BaseAbsActivity mActivity;

    private Unbinder mButterKnife;
    /**
     * 标题栏
     */
    protected FrameLayout mRootView;
    /**
     * 内容区域
     */
    protected LinearLayout mContainerLay;
    /**
     * 标题栏管理器
     */
    protected TitleBarManager mTitleBarManager;

    /**
     * 情感图帮助类
     */
    protected EmptyViewHelper mEmptyViewHelper;

    protected int titleBarPx = ResourceUtil.getDimen(R.dimen.sx48);


    /**
     * 初始化WindowFeature的相关内容
     */
    @Override
    protected void initParentWindowFeature() {
    }

    @Override
    protected int getRootLayoutId() {
        return R.layout.acty_common_parent;
    }

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
        // 绑定View
        mButterKnife = mBaseAbsHelper.bindButterKnife(this);
        // 初始化情感图
        mEmptyViewHelper = new EmptyViewHelper(this);
        mEmptyViewHelper.bind(mContainerLay);

        initView(savedInstanceState, mContainerLay);
    }

    /**
     * 初始化根节点布局视图
     */
    private void initParentView() {
        // 获取标题栏视图
        mRootView = findViewById(R.id.root_view);
        initTitleBar();
        initStatusBar();
        attchContainerToRootView();
        // 加载内容布局到内容容器
        attchToContainerView();
    }

    /**
     * 加载内容容器到根布局
     * 子类实现需继承该布局实现
     */
    protected void attchContainerToRootView() {
        mContainerLay = new LinearLayout(mActivity);
        mContainerLay.setOrientation(LinearLayout.VERTICAL);
        mContainerLay.setId(R.id.custom_btn1);
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.topMargin = mTitleBarManager.getTitleHeight();
        mRootView.addView(mContainerLay, params);
    }

    /**
     * 加载内容布局到内容容器
     */
    private void attchToContainerView() {
        BaseAbsFragment fragment = fragment();
        BaseAbsFragment tag = findFragment(fragment.getClass());
        if (tag == null) {
            loadRootFragment(R.id.custom_btn1, fragment());
        }
    }

    protected abstract BaseAbsFragment fragment();

    /**
     * 初始化状态栏
     */
    protected void initStatusBar() {
        if (mBaseAbsHelper != null) {
            mBaseAbsHelper.initRootStatusBar(this);
        }
    }

    /**
     * 初始化标题栏
     */
    protected void initTitleBar() {
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, titleBarPx);
        mTitleBarManager = new TitleBarManager().with(mRootView, params).listener(this);
        initTitleView();
        initTitleNameView();
    }


    /**
     * 初始化标题栏标题名称
     */
    private void initTitleNameView() {
        // 加载视图内容
        if (mTitleBarManager != null && mBaseAbsHelper != null) {
            String titleName = getTitleName();
            // 写入标题到Activity中
            mBaseAbsHelper.setTitleName(this, titleName);
            // 刷新titlebar
            mTitleBarManager.setTitle(titleName);
        }
    }

    /**
     * 初始化标题栏视图
     * (子类要扩展需要继承该方法实现)
     */
    public void initTitleView() {
        mTitleBarManager.type(TitleBarType.LEFT_CENTER).apply();
    }


    @Override
    public void onDestroy() {
        // 解除注解绑定ButterKnife
        mBaseAbsHelper.unbindButterKnife(mButterKnife);
        if (mBaseAbsHelper != null) {
            mBaseAbsHelper.destoryImmersionBar(this);
        }
        super.onDestroy();
    }

    /**
     * 左项被点击
     *
     * @param v 被点击的左项View
     */
    @Override
    public void onLeftClick(View v) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }

    /**
     * 标题被点击
     *
     * @param v 被点击的标题View
     */
    @Override
    public void onTitleClick(View v) {

    }

    /**
     * 右项被点击
     *
     * @param v 被点击的右项View
     */
    @Override
    public void onRightClick(View v) {

    }

    /**
     * 显示无内容情感图
     */
    public void showNotContentError(String content) {
        mEmptyViewHelper.showNODataView(content, this);
    }

    /**
     * 显示无内容情感图 (下划线文案)
     */
    public void showNotContentError(String content, String underLineText) {
        mEmptyViewHelper.showNODataView(content, null, underLineText, this);
    }

    /**
     * 显示连接失败情感图
     */
    public void showConnectionError() {
        mEmptyViewHelper.showConnectErrorView(this);
    }

    /**
     * 显示网络异常情感图
     */
    public void showNetWorkError() {
        mEmptyViewHelper.showNetworkErrorView(this);
    }

    /**
     * 情感图点击事件(子类实现细节)
     *
     * @param view
     */
    @Override
    public void emptyViewClick(View view, @EmptyViewType int flag) {

    }
}
