package com.ps.yuyue.activity

import android.os.Bundle
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.lc.framework.core.activity.CommonAbsActivity
import com.ps.yuyue.R
import com.ps.yuyue.router.AppRouterContants
import java.io.File

/**
 *
 * 类名：com.ps.yuyue
 * 描述：首页
 *
 * @author liucheng - liucheng@xhg.com
 *
 * @date 2020/3/24 11:30
 */
@Route(path = AppRouterContants.APP_MAIN)
class MainActivity1 : CommonAbsActivity() {
    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun getTitleName(): String {
        return "首页"
    }

    override fun initView(savedInstanceState: Bundle?, containerLay: LinearLayout?) {

    }

    fun main(args: Array<String>?){




    }

    fun change(str:String):String{

        return ""
    }
}