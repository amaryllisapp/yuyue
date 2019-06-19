package com.lc.framework.core.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lc.framework.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.OVER_SCROLL_NEVER;
import static android.widget.LinearLayout.VERTICAL;

/**
 * 类名：com.lc.framework.core.activity
 * 描述：列表抽象基础类
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/18 11:14
 */

public abstract class ListAbsActivity<T> extends CommonAbsActivity implements BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.OnItemChildClickListener, OnRefreshLoadMoreListener {

    protected SmartRefreshLayout mRefreshLayout;

    protected RecyclerView mRecyclerView;

    protected List<T> datas = new ArrayList<>();

    private BaseQuickAdapter mAdapter;
    /**
     * 是否打开加载更多
     */
    private boolean mIsOpenLoadMore = true;
    /**
     * 是否打开自动刷新
     */
    private boolean mIsOpenRefreshOnly = true;


    @Override
    public int layoutId() {
        return R.layout.acty_list_parent;
    }

    public abstract int itemLayoutId();

    public abstract void convertItem(BaseViewHolder baseViewHolder, T t);

    @Override
    protected String getTitleName() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState, LinearLayout containerLay) {
        initRecyclerView(containerLay);
        initAdapter();
        initRefreshView();
    }

    /**
     * 初始化RecyclerView列表
     * @param containerLay
     */
    protected void initRecyclerView(LinearLayout containerLay) {
        if(mAdapter == null){
            mAdapter = new ListBaseAdapter();
        }
        mRefreshLayout = containerLay.findViewById(R.id.base_refresh_layout);
        mRecyclerView = new RecyclerView(mActivity);
        mRecyclerView.setClipToPadding(false);
        mRecyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        mRecyclerView.setItemAnimator(getItemAnimator());
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.addItemDecoration(getItemDecoration());
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.addView(mRecyclerView, new SmartRefreshLayout.LayoutParams(
                SmartRefreshLayout.LayoutParams.MATCH_PARENT, SmartRefreshLayout.LayoutParams.MATCH_PARENT));
    }

    /**
     * 子类ITEM动画
     * 如需其他动画可继承该方法进行扩展
     * @return
     */
    protected RecyclerView.ItemAnimator getItemAnimator(){
        return new DefaultItemAnimator();
    }

    /**
     * 布局显示（网格布局或者线性布局或者流式布局等）
     * @return
     */
    protected RecyclerView.LayoutManager getLayoutManager(){
        return new LinearLayoutManager(mActivity);
    }

    /**
     * 分割线
     * 如需多样化可继承该方法进行扩展
     * @return
     */
    protected RecyclerView.ItemDecoration getItemDecoration(){
        return new DividerItemDecoration(mActivity, VERTICAL);
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mAdapter.setEnableLoadMore(true);
        if (getHeaderView() != null) {
            mAdapter.addHeaderView(getHeaderView());
        }
        if(isOpenLoadAnimation()){
            mAdapter.openLoadAnimation();
        }else{
            mAdapter.closeLoadAnimation();
        }
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
    }

    /**
     * 初始化刷新视图
     */
    private void initRefreshView(){
        mRefreshLayout.setEnableLoadMore(mIsOpenLoadMore);
        mRefreshLayout.setEnableRefresh(mIsOpenRefreshOnly);
        mRefreshLayout.setRefreshHeader(getRefreshHeaderView());
        mRefreshLayout.setRefreshFooter(getRefreshFooterView());
        mRefreshLayout.setEnableHeaderTranslationContent(true);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
    }

    public void setNewData(List<T> data){
        mAdapter.replaceData(data);
    }

    public void addData(List<T> data){
        mAdapter.addData(data);
    }

    public void addData(T data){
        mAdapter.addData(data);
    }

    public void removeData(int position){
        mAdapter.remove(position);
    }

    /**
     * 设置headerView
     * @return
     */
    protected View getHeaderView() {
        return null;
    }

    protected RefreshHeader getRefreshHeaderView(){
        ClassicsHeader header = new ClassicsHeader(mRefreshLayout.getContext());
        return header;
    }

    protected RefreshFooter getRefreshFooterView(){
        ClassicsFooter footer = new ClassicsFooter(mRefreshLayout.getContext());
        return footer;
    }

    /**
     * 是否开启加载动画
     * @return
     */
    protected boolean isOpenLoadAnimation(){
        return false;
    }

    /**
     * 是否开发刷新和加载更多功能
     * 此方法一定要在加载mRefreshLayout等组件之前执行
     * @return
     */
    protected void isOpenRefreshLoadMore(boolean isOpenRefreshLoadMore){
        mIsOpenLoadMore = isOpenRefreshLoadMore;
        mIsOpenRefreshOnly = isOpenRefreshLoadMore;
    }

    /**
     * 仅开启自动刷新功能
     * 此方法一定要在加载mRefreshLayout等组件之前执行
     */
    public void openRefreshOnly(){
        mIsOpenLoadMore = false;
        mIsOpenRefreshOnly = true;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        onItemChildClick(adapter, view, position);
    }

    /**
     * 点击Item时触发
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    /**
     * 刷新时回调
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

    /**
     * 加载更多时回调
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    /**
     * 刷新结束
     */
    protected void refreshComplate(){
        mRefreshLayout.finishRefresh();
    }

    /**
     * 加载更多结束(不再触发加载更多)
     */
    protected void finishLoadMoreWithNoMoreData(){
        mRefreshLayout.finishLoadMoreWithNoMoreData();
    }

    /**
     * 加载更多结束（当次结束）
     */
    protected void finishLoadMore(){
        mRefreshLayout.finishLoadMore();
    }

    /**
     * 适配器
     */
    public class ListBaseAdapter extends BaseQuickAdapter<T, BaseViewHolder> {

        ListBaseAdapter() {
            super(itemLayoutId());
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, T t) {
            convertItem(baseViewHolder, t);
        }
    }

    /**
     * 滚动到顶部
     */
    public void scrollToTop() {
        scrollToPosition(0);
    }

    /**
     * 滚动到置顶position
     *
     * @param position 标志位
     */
    public void scrollToPosition(int position) {
        if (position < 0 || position >= datas.size()) {
            return;
        }
        RecyclerView.LayoutManager mManager = mRecyclerView.getLayoutManager();
        mManager.smoothScrollToPosition(mRecyclerView, null, position);
    }
}
