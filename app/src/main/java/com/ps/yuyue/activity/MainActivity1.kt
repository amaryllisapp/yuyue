package com.ps.yuyue.activity

import android.os.Bundle
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.lc.framework.core.activity.CommonAbsActivity
import com.ps.yuyue.R
import com.ps.yuyue.router.AppRouterContants

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

    fun main(args: Array<String>?) {
        val list = mutableListOf<Book>()
        list.add(Book("十年", "村上春树"))
        for (book in list) {

        }
        for (num in 0 until list.size - 1 step 1) {

        }
        for (num in (list.size - 1) downTo 0) {

        }
        for (num in 0..list.lastIndex) {

        }
        for (num in list.indices) {

        }
        list.clear()

        dealSet()

    }

    fun dealSet(): Unit {
        var la = mutableSetOf<String>("JAVA", "PHP", "delphi", "Perl")
        la.add("C++")
        la.remove("JAVA")
        la.removeAll(setOf("Perl", "PHP"))
        la.clear()
        la.forEach {

        }
    }

    fun dealMap(): Unit {
        var map = mutableMapOf("java" to "89分", "oracle" to "32分")
        for (key in map.keys) {
            //todo:查询所有的key
        }
        for (value in map.values) {
            // TODO:查询所有的Values
        }
        for (entry in map.entries) {
            // TODO:查询所有的实体

        }
        for ((key, value) in map) {

        }
        map.forEach {
            var entry: Map.Entry<String, String> = it
        }
        map.clear()
        map.put("mysl","54分")
        var value = map.get("java")
    }

    class Book(name: String, author: String)
}