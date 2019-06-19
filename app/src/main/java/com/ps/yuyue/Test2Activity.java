package com.ps.yuyue;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lc.framework.core.activity.ListAbsActivity;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_2;
import static com.lc.framework.router.share.ShareRouterContants.PUBLIC_MAIN;

/**
 * 类名：com.ps.yuyue
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/14 16:58
 */
@Route(path = PUBLIC_MAIN)
public class Test2Activity extends ListAbsActivity<Test2Activity.Item> {
    public List<Item> getList() {
        List<Item> list = new ArrayList<>();
        Item item = new Item();
        item.name = "刘承";
        item.value = "性别：男";
        list.add(item);
        Item item1 = new Item();
        item1.name = "刘承111";
        item1.value = "性别：男11";
        list.add(item1);
        return list;
    }


    @Override
    public int itemLayoutId() {
        return simple_list_item_2;
    }

    @Override
    protected void initView(Bundle savedInstanceState, LinearLayout containerLay) {
//        openRefreshOnly();
        super.initView(savedInstanceState, containerLay);
        setNewData(getList());
    }

    @Override
    public void convertItem(BaseViewHolder holder, Item item) {
        holder.setText(android.R.id.text1, item.name)
                .setText(android.R.id.text2, item.value);
    }

    @Override
    protected RefreshHeader getRefreshHeaderView() {
        BezierCircleHeader header = new BezierCircleHeader(mActivity);
        return header;
    }

    @Override
    protected RefreshFooter getRefreshFooterView(){
        BallPulseFooter footer = new BallPulseFooter(mActivity);
        return footer;
    }


    @Override
    public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.finishRefresh();
//                refreshLayout.resetNoMoreData();//setNoMoreData(false);//恢复上拉状态
            }
        }, 2000);
    }

    /**
     * 加载更多时回调
     *
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.finishLoadMore();
                refreshLayout.resetNoMoreData();//setNoMoreData(false);//恢复上拉状态
            }
        }, 2000);
    }

    public class Item {
        public String name;
        public String value;
    }
}
