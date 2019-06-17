package com.lc.framework.core.activity.helper;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.gyf.barlibrary.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.ps.lc.utils.ToastUtil;
import com.ps.lc.utils.eventbus.EventBusManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类名：com.lc.framework.core.activity.helper
 * 描述：抽象工具类
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/17 11:38
 */
public class BaseAbsHelper {
    /**
     * 需要过滤的标题前缀
     */
    public final static String TITLE_FILTER_PREFIX = "TEST_";
    /**
     * 隐藏系统标题栏
     *
     * @param activity
     */
    public void hideSystemTitleBar(AppCompatActivity activity) {
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar == null) {
            return;
        }
        actionBar.hide();
    }

    /**
     * 初始化EventBus
     */
    public void registerEventBus(Object object, boolean enableEventBus) {
        if (enableEventBus) {
            EventBusManager.register(object);
        }
    }

    /**
     * 解绑EventBus
     *
     * @param object
     */
    public void unRegisterEventBus(Object object) {
        EventBusManager.unRegister(object);
    }


    /**
     * 初始化状态栏, Android  6.0以下手机不支持动态设置字体颜色为深色字体
     * <p>
     * (默认为字体暗色，背景白色)
     */
    public void initRootStatusBar(AppCompatActivity activity) {
        //初始化状态栏，字体暗色，背景白色
        ImmersionBar.with(activity)
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
     * 销毁沉浸式状态栏
     *
     * @param activity
     */
    public void destoryImmersionBar(AppCompatActivity activity) {
        // 销毁状态栏
        ImmersionBar.with(activity).destroy();
    }

    /**
     * 仅限于在Activity中调用Butterknife
     *
     * @param activity
     */
    public Unbinder bindButterKnife(AppCompatActivity activity) {
        return ButterKnife.bind(activity);
    }

    public Unbinder bindButterKnife(View view) {
        return ButterKnife.bind(view);
    }

    /**
     * 解除Butterknife绑定
     * @param unbinder
     */
    public void unbindButterKnife(Unbinder unbinder) {
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }

    /**
     * 销毁Toast
     */
    public void destroyToast() {
        //清除Toast
        ToastUtil.closeToast();
    }


    /**
     * 刷新标题（多用于标题需要动态更新的情况）
     */
    public void setTitleName(AppCompatActivity activity, String titleName) {
        if (!TextUtils.isEmpty(titleName) && !titleName.startsWith(TITLE_FILTER_PREFIX)) {
            // 此处设置用于神策获取标题
            activity.setTitle(titleName);
        }
    }

    /**
     * 显示和隐藏 TitleBar
     *
     * @param visible true 显示,反之隐藏
     */
    public void setTitleBarVisible(TitleBar mTitleBar, boolean visible) {
        mTitleBar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }


    public void clearAllView(Window window) {
        ViewGroup view = (ViewGroup) window.getDecorView();
        view.removeAllViews();
    }
}
