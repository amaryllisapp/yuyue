package com.ps.lc.widget.emptyview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ps.lc.widget.R;

/**
 * @author 36077 - liucheng@xhg.com
 * @date 2018/9/11 15:38
 */
public class EmptyView extends FrameLayout {

    protected Button mButton;
    private ViewGroup mRootView;
    private ImageView mImageView;
    private TextView mTitleView;
    private TextView mDetailView;
    /**
     *  操作布局
     */
    private LinearLayout mActionLay;
    /**
     * 扩展布局，在底部添加额外的内容
     */
    private RelativeLayout mExtraLay;
    private View mView;
    /**
     * 情感图布局
     */
    private LinearLayout mEmptyLayout;
    /**
     * 加载中布局
     */
    private LinearLayout mLoadingLayout;
    /**
     * 加载中布局内容
     */
    private TextView mLoadingContent;
    private Context mContext;

    public EmptyView(@NonNull Context context) {
        super(context);
        mContext = context;
        init();

    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.EmptyViewAttr);
        String attrTitleText = arr.getString(R.styleable.EmptyViewAttr_xhg_title_text);
        String attrDetailText = arr.getString(R.styleable.EmptyViewAttr_xhg_detail_text);
        String attrBtnText = arr.getString(R.styleable.EmptyViewAttr_xhg_btn_text);
        arr.recycle();
    }

    private void init() {
        View view = View.inflate(mContext, R.layout.comm_empty_view, null);
        mRootView = view.findViewById(R.id.empty_root);
        mImageView = view.findViewById(R.id.empty_image);
        mTitleView = view.findViewById(R.id.empty_view_title);
        mDetailView = view.findViewById(R.id.empty_view_detail);
        mActionLay = view.findViewById(R.id.empty_action_lay);
        mExtraLay = view.findViewById(R.id.empty_extra_lay);
        mEmptyLayout = view.findViewById(R.id.empty_lay);
        mLoadingLayout = view.findViewById(R.id.loading_lay);
        mLoadingContent = view.findViewById(R.id.empty_view_content);
        addView(view);
    }

    /**
     * 设置操作布局
     *
     * @param view
     * @param onClickListener
     */
    public void setActionView(View view, OnClickListener onClickListener) {
        mActionLay.removeAllViews();
        mView = view;
            mActionLay.addView(view);
            if (onClickListener != null) {
                mView.setClickable(true);
                mView.setOnClickListener(onClickListener);
                setClickable(false);
            } else {
                mView.setClickable(false);

        }
    }

    /**
     * 设置扩展布局
     * @param view
     * @param layoutParams
     */
    public void setExtraLay(View view, RelativeLayout.LayoutParams layoutParams){
        if(view == null){
            mExtraLay.setVisibility(GONE);
            return;
        }
        mExtraLay.removeAllViews();
        mExtraLay.addView(view, layoutParams);
        mExtraLay.setVisibility(VISIBLE);
    }

    /**
     * 清空操作布局
     */
    public void clearActionView() {
        mActionLay.removeAllViews();
    }

    /**
     * 设置全屏幕点击
     *
     * @param onClickListener
     */
    public void setOnFullScreenClick(OnClickListener onClickListener) {
        if (mView != null) {
            mView.setClickable(false);
        }
        setClickable(true);
        setOnClickListener(onClickListener);
    }

    /**
     * 显示emptyView
     *
     * @param titleText       标题的文字，不需要则传null
     * @param detailText      详情文字，不需要则传null
     * @param onClickListener 按钮的onClick监听，不需要则传null
     */
    public void show(@DrawableRes int drawable, String titleText, String detailText, OnClickListener onClickListener) {
        clearActionView();
        setDrawable(drawable);
        setTitleText(titleText);
        setDetailText(detailText);
        setOnFullScreenClick(onClickListener);
        mExtraLay.removeAllViews();
        show();
    }

    /**
     * 显示加载中视图
     * @param content
     */
    public void showLoading(String content) {
        showLoading();
        mLoadingContent.setText(content);
    }
    /**
     * 显示加载布局
     */
    private void showLoading() {
        setVisibility(VISIBLE);
        mRootView.setVisibility(VISIBLE);
        mEmptyLayout.setVisibility(GONE);
        mLoadingLayout.setVisibility(VISIBLE);
    }

    /**
     * 用于显示纯文本的简单调用方法，此时loading、button均被隐藏
     *
     * @param titleText  标题的文字，不需要则传null
     * @param detailText 详情文字，不需要则传null
     */
    public void show(String titleText, String detailText) {
        setTitleText(titleText);
        setDetailText(detailText);
        show();
    }

    /**
     * 显示emptyView，不建议直接使用，建议调用带参数的show()方法，方便控制所有子View的显示/隐藏
     */
    public void show() {
        setVisibility(VISIBLE);
        mRootView.setVisibility(VISIBLE);
        mEmptyLayout.setVisibility(VISIBLE);
        mLoadingLayout.setVisibility(GONE);
    }

    /**
     * 隐藏emptyView
     */
    public void hide() {
        mRootView.setVisibility(GONE);
        mEmptyLayout.setVisibility(GONE);
        mLoadingLayout.setVisibility(GONE);
        this.setVisibility(GONE);
        this.setTitleText(null);
        this.setDetailText(null);
    }

    public boolean isShowing() {
        return getVisibility() == VISIBLE;
    }

    /**
     * 设置情感图
     *
     * @param drawable
     */
    private void setDrawable(int drawable) {
        if (drawable > -1) {
            mImageView.setVisibility(VISIBLE);
            mImageView.setImageResource(drawable);
        }
    }

    private void hideDrawable(){
        mImageView.setVisibility(GONE);
    }

    public void setTitleText(String text) {
        mTitleView.setText(text);
        if (TextUtils.isEmpty(text)) {
            mTitleView.setVisibility(GONE);
        } else {
            mTitleView.setVisibility(VISIBLE);
        }

    }

    public void setDetailText(String text) {
        mDetailView.setText(text);
        if (TextUtils.isEmpty(text)) {
            mDetailView.setVisibility(GONE);
        } else {
            mDetailView.setVisibility(VISIBLE);
        }
    }

    public void setTitleColor(int color) {
        mTitleView.setTextColor(color);
    }

    public void setTitleSize(int size) {
        mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public void setDetailColor(int color) {
        mDetailView.setTextColor(color);
    }

    public void setDetailSize(int size) {
        mDetailView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }
}
