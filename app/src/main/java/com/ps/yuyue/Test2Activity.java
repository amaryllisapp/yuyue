package com.ps.yuyue;

import android.content.ClipData;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lc.framework.core.activity.CommonAbsActivity;
import com.ps.lc.utils.ToastUtil;
import com.ps.yuyue.adapter.BaseRecyclerAdapter;
import com.ps.yuyue.adapter.SmartViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.layout.simple_list_item_2;
import static android.support.v7.widget.DividerItemDecoration.VERTICAL;
import static com.lc.framework.router.share.ShareRouterContants.PUBLIC_MAIN;

/**
 * 类名：com.ps.yuyue
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/14 16:58
 */
@Route(path = PUBLIC_MAIN)
public class Test2Activity extends CommonAbsActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    public int layoutId() {
        return R.layout.acty_refresh_practive;
    }

    @Override
    protected String getTitleName() {
        return "列表";
    }

    @Override
    protected void initView(Bundle savedInstanceState, LinearLayout containerLay) {
        initrecyclerView();
    }

    private void initrecyclerView(){
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity, VERTICAL));
        mRecyclerView.setAdapter(new BaseRecyclerAdapter<Item>(getList(), simple_list_item_2,  this) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, Item model, int position) {
                holder.text(android.R.id.text1, model.name);
                holder.text(android.R.id.text2, model.value);
            }
        });

    }

    public List<Item> getList(){
        List<Item> list = new ArrayList<>();
        Item item = new Item();
        item.name = "刘承";
        item.value = "性别：男";
        list.add(item);
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.showToast(mActivity, position+"");
    }

    public class Item{
        public String name;
        public String value;
    }
}
