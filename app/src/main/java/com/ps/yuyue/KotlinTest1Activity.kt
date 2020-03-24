package com.ps.yuyue

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.lc.framework.core.activity.CommonAbsActivity

/**
 *
 * 类名：com.ps.yuyue
 * 描述：kotlin开发测试页面
 *
 * @author liucheng - liucheng@xhg.com
 *
 * @date 2020/3/17 15:33
 */

class KotlinTest1Activity : CommonAbsActivity() {

    private lateinit var showTime:TextView

    override fun layoutId(): Int {
        return R.layout.activity_main
    }


    override fun getTitleName(): String {
        return "kotlin 测试页面"
    }

    override fun initView(savedInstanceState: Bundle?, containerLay: LinearLayout?) {

    }
}