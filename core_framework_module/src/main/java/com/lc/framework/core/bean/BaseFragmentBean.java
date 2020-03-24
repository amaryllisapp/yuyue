package com.lc.framework.core.bean;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.ps.lc.widget.tab.listener.CustomTabEntity;

/**
 * XHGFragmentAdapter使用的Bean类
 *
 * @author Zenfer
 * @date 2018/9/6 10:31
 */
public class BaseFragmentBean implements CustomTabEntity {
    /**
     * tab的文案
     */
    private String title;
    /**
     * fragment
     */
    private Fragment fragment;
    /**
     * 其他数据类型,如每个tab的消息数等
     */
    private Object data;
    /**
     * tab选中的图标
     */
    private int selectedIcon;
    /**
     * tab默认状态的图标
     */
    private int unSelectedIcon;

    public BaseFragmentBean(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public BaseFragmentBean(String title, Fragment fragment, Object data) {
        this.title = title;
        this.fragment = fragment;
        this.data = data;
    }

    public BaseFragmentBean(String title, Fragment fragment, Object data, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.fragment = fragment;
        this.data = data;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(@NonNull Fragment fragment) {
        this.fragment = fragment;
    }

    public Object getData() {
        return data;
    }

    public void setData(@Nullable Object data) {
        this.data = data;
    }

    public void setSelectedIcon(int selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    public void setUnSelectedIcon(int unSelectedIcon) {
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
