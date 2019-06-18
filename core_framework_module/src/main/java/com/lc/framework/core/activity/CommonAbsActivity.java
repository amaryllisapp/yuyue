package com.lc.framework.core.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lc.framework.R;
import com.ps.lc.utils.widgets.titlebar.OnTitleBarListener;
import com.ps.lc.utils.widgets.titlebar.TitleBarManager;
import com.ps.lc.utils.widgets.titlebar.TitleBarType;

import butterknife.Unbinder;

/**
 * 类名：com.lc.framework.core.activity
 * 描述：通用抽象基类（常用语普通的列表和详情页面）
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/17 14:41
 */
public abstract class CommonAbsActivity extends BaseAbsActivity implements OnTitleBarListener {

    protected BaseAbsActivity mActivity;

    private Unbinder mButterKnife;
    /**
     * 标题栏
     */
    protected FrameLayout mRootView;
    /**
     * 内容区域
     */
    private LinearLayout mContainerLay;
    /**
     * 标题栏管理器
     */
    protected TitleBarManager mTitleBarManager;

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

    /**
     * 初始化根节点布局视图
     */
    private void initParentView() {
        // 获取标题栏视图
        mRootView = findViewById(R.id.root_view);
        // 内容视图容器
        mContainerLay = findViewById(R.id.root_container);
    }

    /**
     * 加载内容布局到内容容器
     */
    private void attchToContainerView() {
        mContainerLay.addView(getLayoutInflater().inflate(layoutId(), mContainerLay, false));
    }

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
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
        mTitleBarManager.type(TitleBarType.LEFT_ONLY).apply();
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
}
