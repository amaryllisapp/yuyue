package com.ps.lc.widget.emptyview;


import android.content.Context;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ps.lc.utils.ResourceUtil;
import com.ps.lc.widget.R;
import com.ps.lc.widget.UnderLineTextView;

/**
 * 情感图Helper类
 *
 * @author 36077 - liucheng@xhg.com
 * @date 2018/9/12 10:16
 */
public class EmptyViewHelper {
    /**
     * 上下文内容
     */
    private Context mContext;
    /**
     * 情感图的父类视图
     */
    private LinearLayout mParentView;
    /**
     * 情感图视图
     */
    private EmptyView mEmptyView;
    /**
     * 主布局
     */
    private ViewGroup mContainerView;
    /**
     * 情感图点击监听事件
     */
    private OnEmptyViewClickListener mEmptyViewClickListener;
    /**
     * 当前使用标签
     */
    private int mCurrentFlag;

    public EmptyViewHelper(Context context) {
        mContext = context;
        mEmptyView = new EmptyView(mContext);
    }

    public OnEmptyViewClickListener getEmptyViewClickListener() {
        return mEmptyViewClickListener;
    }

    public void setEmptyViewClickListener(OnEmptyViewClickListener emptyViewClickListener, @EmptyViewType int flag) {
        this.mEmptyViewClickListener = emptyViewClickListener;
        this.mCurrentFlag = flag;

    }

    /**
     * 绑定UI
     *
     * @param rootView
     */
    public void bind(LinearLayout rootView) {
        mParentView = rootView;
        // 将情感图绑定到内容根目录的第一个位置
        mEmptyView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        mParentView.addView(mEmptyView, 0, mEmptyView.getLayoutParams());

        if (mParentView.getChildCount() > 1) {
            mContainerView = (ViewGroup) mParentView.getChildAt(1);
        }
        mEmptyView.hide();
    }

    /**
     * 显示情感图
     */
    public void showEmptyView() {
        if (mContainerView != null) {
            mContainerView.setVisibility(View.GONE);
        }
        mEmptyView.show();
    }

    /**
     * 隐藏情感图
     */
    public void hideEmptyView() {
        if (mContainerView != null) {
            mContainerView.setVisibility(View.VISIBLE);
        }
        mEmptyView.hide();
    }

    /**
     * 显示加载中界面
     *
     * @param content
     */
    public void loading(String content) {
        showEmptyView();
        mEmptyView.showLoading(content);
    }

    /**
     * 显示无数据显示情感图
     */
    public void showNODataView(String content, OnEmptyViewClickListener emptyViewClickListener) {
        showNODataView(content, null, emptyViewClickListener);
    }

    public void showNODataView(String content, String detailText, OnEmptyViewClickListener emptyViewClickListener) {
        showNODataView(content, detailText, ResourceUtil.getString(R.string.empty_view__back_home), emptyViewClickListener);
    }

    public void showNODataView(String content, String detailText, String underLineText, OnEmptyViewClickListener emptyViewClickListener) {
        setEmptyViewClickListener(emptyViewClickListener, EmptyViewType.TAG_NO_CONTENT);
        showEmptyView();
        mEmptyView.show(
                R.drawable.picture_default_record,
                TextUtils.isEmpty(content) ? ResourceUtil.getString(R.string.empty_view_no_data) : content,
                detailText,
                onClickListener
        );
        initUnderLineActionView(underLineText, R.color.colorPrimary);
    }

    /**
     * 初始化下划线操作View
     *
     * @return
     */
    private void initUnderLineActionView(String content, int color) {
        UnderLineTextView actionView = new UnderLineTextView(mContext);
        actionView.setText(content);
        actionView.setUnderLineColor(color);
        actionView.setTextColor(ResourceUtil.getColor(color));
        actionView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtil.getDimen(R.dimen.sx16));
        // 设置居中
        actionView.setGravity(Gravity.CENTER_HORIZONTAL);
        mEmptyView.setActionView(actionView, onClickListener);
    }

    /**
     * 设置扩展布局
     * @param view
     * @param params
     */
    public void addExtraLayout(View view, RelativeLayout.LayoutParams params){
        mEmptyView.setExtraLay(view, params);
    }

    /**
     * 初始化按钮操作View
     *
     * @param content
     */
    private void initBtnActionView(String content) {

    }


    /**
     * 显示网路错误情感图
     */
    public void showNetworkErrorView(OnEmptyViewClickListener emptyViewClickListener) {
        setEmptyViewClickListener(emptyViewClickListener, EmptyViewType.TAG_NETWOEK_ERROR);
        showEmptyView();
        mEmptyView.show(
                R.drawable.picture_default_wifi,
                ResourceUtil.getString(R.string.empty_view_no_net_error),
                ResourceUtil.getString(R.string.empty_view_click_to_reload),
                onClickListener
        );
    }

    /**
     * 显示连接错误情感图
     */
    public void showConnectErrorView(OnEmptyViewClickListener emptyViewClickListener) {
        setEmptyViewClickListener(emptyViewClickListener, EmptyViewType.TAG_CONNECTION_ERROR);
        showEmptyView();
        mEmptyView.show(
                R.drawable.picture_load_failed,
                ResourceUtil.getString(R.string.empty_view_netwrk_error),
                ResourceUtil.getString(R.string.empty_view_click_to_reload),
                onClickListener);
    }

    /**
     * 定位失败情感图
     */
    public void showLocationErrorView(OnEmptyViewClickListener emptyViewClickListener) {
        /*setEmptyViewClickListener(emptyViewClickListener, EmptyViewType.TAG_LOCATION_ERROR);
        showEmptyView();
        mEmptyView.show(
                R.drawable.icon_location_failed,
                ResourceUtil.getString(R.string.location_msg_failure),
                ResourceUtil.getString(R.string.location_btn_txt),
                onClickListener);*/
    }

    /**
     * 自定义情感图
     */
    public void showCustomErrorView(@DrawableRes int drawable, String title, String detail, View actionView, OnEmptyViewClickListener emptyViewClickListener) {
        setEmptyViewClickListener(emptyViewClickListener, EmptyViewType.TAG_CUSTOM_ERROR);
        showEmptyView();
        mEmptyView.setActionView(actionView, onClickListener);
        mEmptyView.show(
                drawable,
                title,
                detail,
                onClickListener);
    }

    /**
     * 点击事件
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mEmptyViewClickListener.emptyViewClick(v, mCurrentFlag);
        }
    };


}
