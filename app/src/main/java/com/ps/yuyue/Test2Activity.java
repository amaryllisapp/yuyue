package com.ps.yuyue;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lc.framework.core.activity.SimpleListActivity;
import com.lc.framework.core.activity.listener.BaseAdapterListener;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_2;
import static com.lc.framework.router.share.ShareRouterContants.PUBLIC_MAIN;

/**
 * 类名：com.ps.yuyue
 * 描述：测试列表页面
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/14 16:58
 */
@Route(path = PUBLIC_MAIN)
public class Test2Activity extends SimpleListActivity<Test2Activity.Item> implements BaseAdapterListener<Test2Activity.Item> {
    private int maxSize = 2;
    private int page = 0;
    private int pagesize = 10;

    public List<Item> getList() {
        if (page > maxSize) {
            return null;
        }
        if (page == maxSize) {
            pagesize = 5;
        }
        List<Item> list = new ArrayList<>();
        for (int i = 0; i < pagesize; i++) {
            Item item = new Item();
            item.name = "刘承" + page + "" + i;
            item.value = "性别：男" + page + "" + i;
            list.add(item);
        }
        return list;
    }

    @Override
    protected String getTitleName() {
        return "列表页面";
    }

    @Override
    protected void initView(Bundle savedInstanceState, LinearLayout containerLay) {
        setAutoRefresh(false);
        setNoContentTips("貌似没有您的数据哦~");
//        setAdapter(new Test2Adapter(this));
        super.initView(savedInstanceState, containerLay);
    }


    @Override
    public int itemLayoutId() {
        return simple_list_item_2;
    }

    @Override
    protected void loadData(int state, int page) {
        this.page = page;
        setListData(getList());
    }

    /**
     * 设置动画是否开启
     * @return
     */
    @Override
    protected boolean setOpenLoadAnimation() {
        return true;
    }

    @Override
    public void convertItem(BaseViewHolder holder, Item item) {
        holder.setText(android.R.id.text1, item.name)
                .setText(android.R.id.text2, item.value);
    }


    public class Item {
        public String name;
        public String value;
    }
}
