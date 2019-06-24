package com.lc.framework.core.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lc.framework.core.activity.helper.BaseAbsHelper;
import com.ps.lc.widget.emptyview.EmptyViewHelper;

import butterknife.Unbinder;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 类名：com.lc.framework.core.activity
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/24 10:52
 */
public abstract class BaseAbsFragment extends SupportFragment {

    protected BaseAbsHelper mBaseAbsHelper;

    private ViewGroup mRootView;

    private Unbinder mButterKnife;

    /**
     * 情感图帮助类
     */
    protected EmptyViewHelper mEmptyViewHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBaseAbsHelper = new BaseAbsHelper();

        mRootView = (ViewGroup) inflater.inflate(layout(), container, false);
        // EventBus注册
        mBaseAbsHelper.registerEventBus(this, getEventBusEnable());
        // 设置转场动画
        setFragmentAnimator(getFragAnimator());
        // 绑定View
        mButterKnife = mBaseAbsHelper.bindButterKnife(this, mRootView);
        // 初始化情感图
        mEmptyViewHelper = new EmptyViewHelper(getContext());

        mEmptyViewHelper.bind(mRootView);

        initView(savedInstanceState, mRootView);
        return mRootView;
    }

    /**
     * 抛出帮助类，便于在此类没有处理的内容，可直接UI界面进行处理
     *
     * @return
     */
    public BaseAbsHelper getBaseAbstractHelper() {
        return mBaseAbsHelper;
    }

    /**
     * 获取超类布局ID
     *
     * @return
     */
    protected abstract int layout();

    protected abstract void initView(Bundle savedInstanceState, ViewGroup containerLay);

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
     * 设置Fragment转场动画
     * <p>
     * 横向转场动画：DefaultHorizontalAnimator(默认)
     * 纵向转场动画：DefaultVerticalAnimator
     * 无转场动画：DefaultNoAnimator
     * 自定义转场动画：继承FragmentAnimator自己实现
     *
     * @return
     */
    protected FragmentAnimator getFragAnimator() {
        return new DefaultHorizontalAnimator();
    }

    /**
     * 解除绑定时(类似于Activity中的onDestory)
     */
    @Override
    public void onDetach() {
        // 解除EventBus注册
        if (mBaseAbsHelper != null) {
            mBaseAbsHelper.unRegisterEventBus(this);
        }
        if (mButterKnife != null) {
            mButterKnife.unbind();
            mButterKnife = null;
        }
        if (mBaseAbsHelper != null) {
            mBaseAbsHelper = null;
        }
        super.onDetach();
    }
}
