package com.lc.framework.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;

import com.lc.framework.R;
import com.ps.lc.utils.ListUtils;
import com.ps.lc.utils.NetworkUtil;
import com.ps.lc.utils.ResourceUtil;
import com.ps.lc.utils.ToastUtil;
import com.ps.lc.widget.emptyview.EmptyViewType;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import java.util.List;


/**
 * 类名：com.lc.framework.core.activity
 * 描述：继承通用的ListAbsActivity，实现和业务相关的列表逻辑实现
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/20 10:54
 */
public abstract class SimpleListActivity<T> extends ListAbsActivity<T> {
    /**
     * 默认页码
     */
    private final static int mDefaultPageNum = 0;
    /**
     * 默认每页显示数
     */
    private final static int mDefaultPageSize = 10;
    /**
     * 当前页码，默认为0
     */
    private int mPageNum = mDefaultPageNum;
    /**
     * 每页显示数
     */
    private int mPageSize = mDefaultPageSize;

    /**
     * 默认,初始化状态
     */
    protected final static int DEFAULT = 0;
    /**
     * 刷新状态
     */
    protected final static int REFRESH = 1;
    /**
     * 加载更多状态
     */
    protected final static int LOADMORE = 2;

    /**
     * 0：初始化加载， 1：刷新时加载；2：加载更多时加载
     */
    private int state = DEFAULT;
    /**
     * 无内容情感图提示语
     */
    private String mNoContentTips = ResourceUtil.getString(R.string.srl_footer_nothing);
    /**
     * 是否自动刷新
     */
    private boolean autoRefresh;

    @Override
    protected void initView(Bundle savedInstanceState, LinearLayout containerLay) {
        super.initView(savedInstanceState, containerLay);
        if(autoRefresh){
            autoRefresh();
        }
    }

    @Override
    protected RefreshHeader getRefreshHeaderView() {
        BezierCircleHeader header = new BezierCircleHeader(mActivity);
        return header;
    }

    @Override
    protected RefreshFooter getRefreshFooterView() {
        ClassicsFooter footer = new ClassicsFooter(mActivity);
        footer.setSpinnerStyle(SpinnerStyle.Translate);
        footer.setProgressDrawable(ResourceUtil.getDrawable(R.drawable.ic_progress_puzzle));
        return footer;
    }

    @Override
    public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
        resetPage();
        loadData(state = REFRESH, mPageNum + 1);
    }

    /**
     * 加载更多时回调
     *
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        loadData(state = LOADMORE, mPageNum + 1);
    }

    /**
     * 重置分页数据
     */
    private void resetPage() {
        mPageNum = mDefaultPageNum;
        mPageSize = mDefaultPageSize;
    }

    /**
     * 加载数据（本地数据或者远程数据）
     *
     * @param state
     */
    protected abstract void loadData(int state, int page);

    /**
     * 如果刚好是一页数据，会多加载一次数据
     *
     * @param data
     */
    protected void setListData(List<T> data) {
        setListData(data, false);
    }

    /**
     * 设置无内容情感图提示
     *
     * @param str
     */
    protected void setNoContentTips(String str) {
        this.mNoContentTips = str;
    }

    /**
     * 设置列表数据
     *
     * @param data
     * @param isLoadMore
     */
    protected void setListData(List<T> data, boolean isLoadMore) {
        if (!NetworkUtil.isNetworkConnected()) {
            showNetWorkError();
            finishLoadMoreWithNoMoreData();
            return;
        }
        if (ListUtils.isEmpty(data)) {
            showNotContentError(mNoContentTips);
            finishLoadMoreWithNoMoreData();
            return;
        }

        mPageNum++;
        // TODO：返回的数据量比设定的要大如何处理呢？
        if (state == LOADMORE) {
            addData(data);
        } else {
            setNewData(data);
        }
        // 结束刷新
        if(state != LOADMORE){
            finishRefresh(2000);
            return;
        }

        if (state == LOADMORE && !isLoadMore) {
            finishLoadMoreWithNoMoreData();
            return;
        }

        if (data.size() == mPageSize) {
            finishLoadMore();
            return;
        }
        finishLoadMoreWithNoMoreData();
    }

    protected void setAutoRefresh(boolean enable){
        this.autoRefresh = enable;
    }

    protected void setPageSize(int pageSize){
        this.mPageSize = pageSize;
    }

    /**
     * 情感图点击事件(子类实现细节)
     *
     * @param view
     */
    @Override
    public void emptyViewClick(View view, @EmptyViewType int flag) {
        super.emptyViewClick(view, flag);
        switch (flag) {
            case EmptyViewType.TAG_NO_CONTENT:
                //返回首页
//                IntentManager.intentToMainActivity();
                ToastUtil.showToast(mActivity, "您点击了返回首页按钮");
                break;
            case EmptyViewType.TAG_NETWOEK_ERROR:
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                break;
            case EmptyViewType.TAG_CONNECTION_ERROR:
                //重新刷新数据
//                showLoading();
                if (state != LOADMORE) {
                    resetPage();
                }
                loadData(state, mPageNum + 1);
                break;
            default:
        }
    }
}
