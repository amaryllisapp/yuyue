package com.lc.framework.core.activity.fragment.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.lc.framework.core.bean.BaseFragmentBean;

import java.util.List;

/**
 * 通用的 FragmentAdapter
 *
 * @author Zenfer
 * @date 2018/9/5 9:53
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private List<BaseFragmentBean> mFragments;
    private Fragment mCurrentFragment;
    private int currentPosition;

    /**
     * @param fm        fragment管理器
     * @param fragments Fragment List , Size > 0
     */
    public FragmentAdapter(@NonNull FragmentManager fm,
                           @NonNull List<BaseFragmentBean> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment = (Fragment) object;
        currentPosition = position;
        super.setPrimaryItem(container, position, object);
    }

    /**
     * 获取 FragmentList
     */
    public List<BaseFragmentBean> getFragments() {
        return mFragments;
    }

    /**
     * 获取 当前的 Fragment
     */
    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    /**
     * 获取 当前的position
     */
    public int getCurrentPosition() {
        return currentPosition;
    }
}
