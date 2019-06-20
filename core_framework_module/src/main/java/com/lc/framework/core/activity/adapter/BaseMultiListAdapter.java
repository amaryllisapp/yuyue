package com.lc.framework.core.activity.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lc.framework.core.activity.listener.BaseMultiAdapterListener;
import com.ps.lc.utils.ListUtils;

import java.util.List;

/**
 * 类名：com.ps.yuyue.adapter
 * 描述：多类型适配器，通过该适配器可以在List中显示不同类型的的数据及布局的列表
 *
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/20 15:21
 */
public class BaseMultiListAdapter<T extends MultiItemEntity> extends BaseMultiItemQuickAdapter<T, BaseViewHolder> {

    private BaseMultiAdapterListener mBaseAdapterListener;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param  baseAdapterListener  new list is created out of this one to avoid mutable list
     */
    public BaseMultiListAdapter(BaseMultiAdapterListener baseAdapterListener) {
        super(null);
        this.mBaseAdapterListener = baseAdapterListener;
        List<Integer> types = mBaseAdapterListener.itemsType();
        List<Integer> layoutIdRes = mBaseAdapterListener.itemsLayout();
        if(ListUtils.isNotEmpty(types)){
            for(int i=0;i< types.size();i++){
                addItemType(types.get(i), layoutIdRes.get(i));
            }
        }
    }


    @Override
    protected void convert(BaseViewHolder helper, T item) {
        mBaseAdapterListener.convertItem(helper, item);
    }
}
