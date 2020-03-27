package com.ps.yuyue.activity

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.View.GONE
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import com.gyf.barlibrary.ImmersionBar
import com.lc.framework.core.activity.CommonAbsActivity
import com.ps.lc.utils.ToastUtil
import com.ps.yuyue.R
import com.ps.yuyue.router.AppIntentManager

/**
 *
 * 类名：com.ps.yuyue
 * 描述：闪屏页面
 *
 * @author liucheng - liucheng@xhg.com
 *
 * @date 2020/3/24 10:50
 */
class SplashActivity : CommonAbsActivity() {

    @BindView(R.id.splash_text)
    lateinit var mSplasshTv: TextView

    override fun layoutId(): Int {
        return R.layout.activity_splash
    }

    override fun getTitleName(): String {
        return "闪屏页面"
    }

    /**
     * 初始化WindowFeature的相关内容
     */
    override fun initParentWindowFeature() {

    }

    override fun initStatusBar() {
        // 隐藏标题栏
        mTitleBarManager.titleBar.visibility = GONE

        //初始化状态栏，字体暗色，背景白色
        ImmersionBar.with(mActivity)
            .fullScreen(true)           // 设置全屏
            .fitsSystemWindows(true)           // 设置状态栏背景颜色
            .statusBarDarkFont(true)     // 设置深色字体
            .keyboardEnable(true)           //初始化，默认透明状态栏和黑色导航栏
            .init()
    }

    override fun initView(savedInstanceState: Bundle?, containerLay: LinearLayout?) {
        Handler().postDelayed(Runnable {
            ToastUtil.showToast(mActivity, "我触发了点击事件")
            mSplasshTv.setText("- 我触发了跳转事件 !")
            AppIntentManager.intentToMainActivity()
//            AppIntentManager.intentToFragment1Activity()
            finish()
        }, 3000)

    }
}