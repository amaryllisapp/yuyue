package com.lc.framework.core.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.lc.framework.R;
import com.lc.framework.core.activity.fragment.adapter.FragmentAdapter;
import com.lc.framework.core.bean.BaseFragmentBean;
import com.ps.lc.utils.ListUtils;
import com.ps.lc.utils.widgets.titlebar.OnTitleBarListener;
import com.ps.lc.widget.emptyview.OnEmptyViewClickListener;
import com.ps.lc.widget.tab.CommonTabLayout;
import com.ps.lc.widget.tab.listener.OnTabSelectListener;

import java.util.ArrayList;

/**
 * 类名：com.lc.framework.core.activity
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/24 16:27
 */
public abstract class TabFragmentActivity extends CommonAbsActivity implements OnTitleBarListener, OnEmptyViewClickListener {

    /**
     * TabLayout
     */
    private CommonTabLayout mTab;
    /**
     * TabLayout 和 Viewpager 之间的分割线
     */
    private View mDividerViewpager;
    /**
     * Viewpager
     */
    private ViewPager mViewPager;
    /**
     * FragmentAdapter
     */
    private FragmentAdapter mAdapter;

    /**
     * 当前 Viewpager的 position
     */
    private int currentPosition = 0;
    /**
     * FragmentBeans
     */
    private ArrayList<BaseFragmentBean> mBeans;

    @Override
    public int layoutId() {
        return R.layout.acty_tab_parent;
    }

    @Override
    protected void initView(Bundle savedInstanceState, LinearLayout containerLay) {
        // 初始化基类View
        initBaseFragment(containerLay);
    }

    private void initBaseFragment(LinearLayout containerLay) {
        mDividerViewpager = containerLay.findViewById(R.id.base_divider_viewpager);
        mBeans = loadFragmentBeans();
        if (!ListUtils.isEmpty(mBeans)) {
            throw new IllegalArgumentException("BaseFragmentBean's Size Can't be > 1");
        }
        //初始化TabLayout
        initTabLayout();
        //初始化Viewpager
        initViewPager();
    }

    public ArrayList<BaseFragmentBean> getFragmentBeans() {
        return mBeans;
    }
    /**
     * 初始化 TabLayout
     */
    private void initTabLayout() {
        mTab = mContainerLay.findViewById(R.id.tab);
        mTab.setTabData(mBeans);
        mTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }
    /**
     * 初始化 Viewpager
     */
    private void initViewPager() {
        mViewPager = mContainerLay.findViewById(R.id.viewPager);
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), mBeans);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mTab.setCurrentTab(position);
                setTabPageTag(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }



    /**
     * 获取 TabLayout
     */
    public CommonTabLayout getTabLayout() {
        return mTab;
    }

    /**
     * 設置當前頁碼
     *
     * @param position 当前position
     */
    private void setTabPageTag(int position) {
        currentPosition = position;
        onPageSelected(position);
    }

    /**
     * 获取FragmentAdapter
     *
     * @return FragmentAdapter
     */
    public FragmentAdapter getAdapter() {
        return mAdapter;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    /**
     * 设置 CommomTabLayout 和 Viewpager 之间的分割线是否隐藏和显示
     *
     * @param isVisible true 显示，反之隐藏
     */
    protected void setViewpagerDividerVisible(boolean isVisible) {
        mDividerViewpager.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * 加载ViewPager需要设置的Fragments 和 TabLayout 的 titles
     *
     * @return FragmentBeans
     */
    @NonNull
    abstract protected ArrayList<BaseFragmentBean> loadFragmentBeans();

    /**
     * Viewpager的滑动监听回调方法
     *
     * @param position 当前 ViewPager 的 Position
     */
    protected void onPageSelected(int position) {

    }
}
