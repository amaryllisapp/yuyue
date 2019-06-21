package com.lc.framework.core.activity.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lc.framework.core.activity.listener.BaseMultiAdapterListener;
import com.ps.lc.utils.ListUtils;

import java.util.List;

/**
 * 类名：com.ps.yuyue.adapter
 * 描述：多类型适配器，通过该适配器可以在List中显示不同类型的的数据及布局的列表
 *   1. 劣势：BaseMultipleItemRvListener为这个适配器的升级版本，避免了所有的业务均堆在一个适配器中执行。
 *   2. 优势：在种类并不是很多，且不复杂的情况下，可采用此适配器。实现简单，不需要提供不同的provider辅助实现。
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
     * @param  data  new list is created out of this one to avoid mutable list
     */
    public BaseMultiListAdapter(BaseMultiAdapterListener baseAdapterListener, @Nullable List<T> data) {
        super(data);
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
