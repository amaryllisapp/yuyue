package com.lc.framework.core.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lc.framework.core.activity.helper.BaseAbsHelper;
import com.lc.framework.utils.LeakCanaryUtil;
import com.ps.lc.utils.ActivityUtil;
import com.ps.lc.utils.log.LogHelper;
import com.ps.lc.widget.emptyview.EmptyViewHelper;

import butterknife.Unbinder;

/**
 * 类名：BaseAbsActivity
 * 描述：所有的基础类都需要继承该类执行
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/17 11:36
 */
public abstract class BaseAbsActivity extends SupportAbsActivity {

    protected BaseAbsHelper mBaseAbsHelper;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        mBaseAbsHelper = new BaseAbsHelper();
        // 初始化页面全局化参数
        initParentWindowFeature();
        super.onCreate(savedInstanceState);
        // 隐藏系统标题栏
        mBaseAbsHelper.hideSystemTitleBar(this);
        setContentView(getRootLayoutId());

        // EventBus注册
        mBaseAbsHelper.registerEventBus(this, getEventBusEnable());
        // 添加Activity到Act池
        ActivityUtil.addActivity(this);
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

        // 清除Toast
        mBaseAbsHelper.destroyToast();
        // 解除EventBus注册
        mBaseAbsHelper.unRegisterEventBus(this);
        // 清除所有的View，避免出现有View无法不清除造成的内存泄漏问题
        mBaseAbsHelper.clearAllView(getWindow());
        if (mBaseAbsHelper != null) {
            mBaseAbsHelper = null;
        }
        super.onDestroy();
        //LeakCanary 内存泄漏监听
        LeakCanaryUtil.watchActivity(this);
        LogHelper.i("destroy()", "页面销毁完成");
    }


}
