package com.lc.framework.core.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.lc.framework.R;
import com.ps.lc.utils.ActivityUtil;
import com.ps.lc.utils.KeyboardUtil;
import com.ps.lc.utils.ToastUtil;
import com.ps.lc.utils.eventbus.EventBusManager;
import com.ps.lc.utils.eventbus.EventBusParams;
import com.ps.lc.utils.log.LogHelper;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类名：com.lc.framework.core.activity
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/14 18:08
 */
public abstract class BaseAbstractActivity extends AppCompatActivity {
    /**
     * 需要过滤的标题前缀
     */
    public final static String TITLE_FILTER_PREFIX = "TEST_";
    /**
     * 内容布局
     */
    protected LinearLayout mRootView;
    /**
     * 内容布局
     */
    protected LinearLayout mContentView;
    /**
     * 自定义标题布局
     */
    public CommonTitleBar mTitleBar;
    /**
     * 标题栏分割线
     */
    protected View mTitleDivider;
    /**
     * 情感图帮助类
     */
//    public EmptyViewHelper mEmptyViewHelper;
    /**
     * ButterKnife实例
     */
    private Unbinder mButterKnife;

    protected View mTopView;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        initParentWindowFeature();
        super.onCreate(savedInstanceState);
        // 隐藏系统标题栏
        hideSystemTitleBar();
        setContentView(R.layout.acty_parent);
        initParentView();
        initRootData();
        initView(savedInstanceState, mContentView);
    }

    protected void initParentWindowFeature() {
    }

    /**
     * 初始化视图
     */
    private void initParentView() {
        initRootView();
        initRootTitle();
        initRootStatus();
    }

    /**
     * 初始化数据
     */
    public void initRootData() {
        if (getEventBusEnable()) {
            EventBusManager.register(this);
        }
        ActivityUtil.addActivity(this);
        mButterKnife = ButterKnife.bind(this);
    }

    /**
     * 初始化状态栏, Android  6.0以下手机不支持动态设置字体颜色为深色字体
     * <p>
     * (默认为字体暗色，背景白色)
     */
    public void initRootStatus() {
        //初始化状态栏，字体暗色，背景白色
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                // 设置状态栏背景颜色
                .statusBarColor(android.R.color.white)
                // 设置深色字体
                .statusBarDarkFont(true, 0.3f)
                // 兼容输入框遮挡问题
                .keyboardEnable(true)
                //初始化，默认透明状态栏和黑色导航栏
                .init();
    }

    /**
     * 初始化标题栏
     */
    private void initRootTitle() {
        // 获取标题栏视图
//        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        // 加载视图内容
//        if (mTitleBar != null) {
//            refreshTitle();
//        }
        // 获取标题栏底部的分割线视图
//        mTitleDivider = findViewById(R.id.title_divider_line);
    }

    /**
     * 刷新标题（多用于标题需要动态更新的情况）
     */
    protected void refreshTitle() {
        String titleName = getTitleName();
        if (!TextUtils.isEmpty(titleName) && !titleName.startsWith(TITLE_FILTER_PREFIX)) {
            // 此处设置用于神策获取标题
            setTitle(titleName);
        }
        /*mTitleBar.setTitle(titleName);
        mTitleBar.setOnBackButtonClickListener(this);*/
    }

    /**
     * 显示和隐藏 TitleBar
     *
     * @param visible true 显示,反之隐藏
     */
    public void setTitleBarVisible(boolean visible) {
        mTitleBar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 初始化View
     */
    private void initRootView() {
        mRootView = findViewById(R.id.root_parent);
        mTopView = findViewById(R.id.top_view);
        mContentView = findViewById(R.id.root_container);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(this).inflate(layoutId(), null);
        view.setClickable(true);
        mContentView.addView(view, layoutParams);
        // 初始化情感图
        /*mEmptyViewHelper = new EmptyViewHelper(this);
        mEmptyViewHelper.bind(mContentView);*/
        loadingView();
    }

    private void hideSystemTitleBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return;
        }
        actionBar.hide();
    }

    @Override
    public void onBackButtonClick() {
        //TODO 此处KeyboardUtil会有内存泄漏问题
        KeyboardUtil.hideInputMethod(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }

    @Override
    public void onDestroy() {
        // 销毁状态栏
        ImmersionBar.with(this).destroy();
        // 解除注解绑定
        if (mButterKnife != null) {
            mButterKnife.unbind();
            mButterKnife = null;
        }
        //清除Toast
        ToastUtil.closeToast();
        // EventBus解除绑定
        EventBusManager.unRegister(this);
        // Activity栈中移除当前活动
        ActivityUtil.removeActivity(this);
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.onDestroy();
        //LeakCanary 监听
//        LeakCanaryUtil.watchActivity(this);
        LogHelper.i("returnback", "退出完成");
    }

    /**
     * 接收推送的消息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusParams event) {
        //通用监听事件
        notifyEventBus(event);
    }

    /**
     * 监听通用事件（子类实现）
     *
     * @param event
     */
    public void notifyEventBus(EventBusParams event) {

    }

    /**
     * 获取是否注册EventBus
     * 默认为False，处于关闭状态，如果需要则开启(true),子类重写该方法
     *
     * @return
     */
    protected boolean getEventBusEnable() {
        return false;
    }

    /**
     * 运营拓展界面
     *
     * @param type 类型
     * @param url  H5链接
     */
    public void notifyExtrasPages(String type, Object url) {
    }

    /**
     * 加载布局文件
     *
     * @return
     */
    protected abstract @LayoutRes
    int layoutId();

    /**
     * 获取标题名称
     *
     * @return
     */
    protected abstract String getTitleName();

    protected void loadingView() {
    }

    /**
     * 初始化布局（初始化业务视图）
     *
     * @param rootView
     */
    public abstract void initView(Bundle savedInstanceState, LinearLayout rootView);
}
