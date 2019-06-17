package com.lc.framework.core.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.lc.framework.R;

import butterknife.Unbinder;

/**
 * 类名：com.lc.framework.core.activity
 * 描述：通用抽象基类（常用语普通的列表和详情页面）
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/17 14:41
 */
public abstract class CommonAbsActivity extends BaseAbsActivity implements OnTitleBarListener {

    public BaseAbsActivity mActivity;

    private Unbinder mButterKnife;
    /**
     * 页面根节点View
     */
    private FrameLayout mRootView;
    /**
     * 用于适配沉浸式状态栏
     */
    private View mTopView;
    /**
     * 标题栏
     */
    private TitleBar mTitleBar;
    /**
     * 标题栏底部的分割线视图
     */
    private View mTitleDivider;
    /**
     * 内容区域
     */
    private LinearLayout mContainerLay;

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
        initStatusBar();
        initTitleBar();
        // 绑定View
        mButterKnife = mBaseAbsHelper.bindButterKnife(this);
        initView(savedInstanceState, mContainerLay);
    }

    /**
     * 初始化根节点布局视图
     */
    private void initParentView() {
        // 布局根节点
        mRootView = findViewById(R.id.root_view);
        // 用于适配沉浸式状态栏
        mTopView = findViewById(R.id.top_view);
        // 获取标题栏视图
        mTitleBar = findViewById(R.id.title_bar);
        // 标题栏底部的分割线视图
        mTitleDivider = findViewById(R.id.title_divider_line);
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
        initTitleNameView();
        initTitleView();
    }


    /**
     * 初始化标题栏标题名称
     */
    private void initTitleNameView() {
        // 加载视图内容
        if (mTitleBar != null && mBaseAbsHelper != null) {
            String titleName = getTitleName();
            // 写入标题到Activity中
            mBaseAbsHelper.setTitleName(this, titleName);
            // 刷新titlebar
            mTitleBar.setTitle(titleName);
        }
    }

    /**
     * 初始化标题栏视图
     * (子类要扩展需要继承该方法实现)
     */
    public void initTitleView() {
        mTitleBar.setLeftTitle("返回");
        mTitleBar.setLeftIcon(R.drawable.icon_com_back);
        mTitleBar.setOnTitleBarListener(this);
    }

    /**
     * 显示和隐藏 TitleBar
     *
     * @param visible true 显示,反之隐藏
     */
    public void setTitleBarVisible(boolean visible) {
        mTitleBar.setVisibility(visible ? View.VISIBLE : View.GONE);
        mTitleDivider.setVisibility(visible ? View.VISIBLE : View.GONE);
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
