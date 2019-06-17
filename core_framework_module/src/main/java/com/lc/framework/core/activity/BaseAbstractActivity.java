package com.lc.framework.core.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lc.framework.core.activity.helper.BaseAbstractHelper;
import com.lc.framework.utils.LeakCanaryUtil;
import com.ps.lc.utils.ActivityUtil;
import com.ps.lc.utils.log.LogHelper;

import butterknife.Unbinder;

/**
 * 类名：BaseAbstractActivity
 * 描述：所有的基础类都需要继承该类执行
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/17 11:36
 */
public abstract class BaseAbstractActivity extends AppCompatActivity {

    private Unbinder mButterKnife;

    protected BaseAbstractHelper mBaseAbstractHelper;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        mBaseAbstractHelper = new BaseAbstractHelper();
        // 初始化页面全局化参数
        initParentWindowFeature();
        super.onCreate(savedInstanceState);
        // 隐藏系统标题栏
        mBaseAbstractHelper.hideSystemTitleBar(this);
        setContentView(getRootLayoutId());
        // 绑定View
        mButterKnife = mBaseAbstractHelper.bindButterKnife(this);
        // EventBus注册
        mBaseAbstractHelper.registerEventBus(this, getEventBusEnable());
        // 添加Activity到Act池
        ActivityUtil.addActivity(this);
    }

    /**
     * 抛出帮助类，便于在此类没有处理的内容，可直接UI界面进行处理
     *
     * @return
     */
    public BaseAbstractHelper getBaseAbstractHelper() {
        return mBaseAbstractHelper;
    }

    /**
     * 初始化WindowFeature的相关内容
     */
    protected abstract void initParentWindowFeature();

    /**
     * 获取超类布局ID
     *
     * @return
     */
    protected abstract int getRootLayoutId();

    /**
     * 获取是否注册EventBus
     * 默认为False，处于关闭状态，如果需要则开启(true),子类重写该方法
     *
     * @return
     */
    protected boolean getEventBusEnable() {
        return false;
    }


    @Override
    public void onDestroy() {
        // Activity栈中移除当前活动
        ActivityUtil.removeActivity(this);
        // 解除注解绑定ButterKnife
        mBaseAbstractHelper.unbindButterKnife(mButterKnife);
        // 清除Toast
        mBaseAbstractHelper.destroyToast();
        // 解除EventBus注册
        mBaseAbstractHelper.unRegisterEventBus(this);
        // 清除所有的View，避免出现有View无法不清除造成的内存泄漏问题
        mBaseAbstractHelper.clearAllView(getWindow());
        super.onDestroy();
        //LeakCanary 内存泄漏监听
        LeakCanaryUtil.watchActivity(this);
        LogHelper.i("destroy()", "页面销毁完成");
    }


}
