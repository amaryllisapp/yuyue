package com.lc.framework.core.activity.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseSectionMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.SectionMultiEntity;
import com.lc.framework.core.activity.listener.BaseMultiGroupAdapterListener;
import com.ps.lc.utils.ListUtils;

import java.util.List;

/**
 *  
 * 类名：BaseMultipleItemGroupAdapter
 * 描述：该适配器可以用来处理不同的分组下对应的多个类型的布局
 * 使用场景：树形结构列表
 *
 * @author liucheng - liucheng@xhg.com
 *
 * @date 2019/6/24 10:28
 */
public class BaseMultipleItemGroupAdapter<T extends SectionMultiEntity> extends BaseSectionMultiItemQuickAdapter<T, BaseViewHolder> {

    private BaseMultiGroupAdapterListener mBaseMultiGroupAdapterListener;

    public BaseMultipleItemGroupAdapter(@NonNull BaseMultiGroupAdapterListener baseMultiGroupAdapterListener) {
        this(baseMultiGroupAdapterListener, null);
    }

    /**
     * init BaseMultipleItemGroupAdapter
     * 1. add your header resource layout
     * 2. add some kind of items
     *
     * @param mBaseMultiGroupAdapterListener 适配器
     * @param data                           A new list is created out of this one to avoid mutable list
     */
    public BaseMultipleItemGroupAdapter(@NonNull BaseMultiGroupAdapterListener mBaseMultiGroupAdapterListener, @Nullable List data) {
        super(mBaseMultiGroupAdapterListener.itemLayoutId(), data);
        this.mBaseMultiGroupAdapterListener = mBaseMultiGroupAdapterListener;
        List<Integer> types = mBaseMultiGroupAdapterListener.itemsType();
        List<Integer> layoutIdRes = mBaseMultiGroupAdapterListener.itemsLayout();
        if (ListUtils.isNotEmpty(types)) {
            for (int i = 0; i < types.size(); i++) {
                addItemType(types.get(i), layoutIdRes.get(i));
            }
        }
    }

    /**
     * 处理分组头部填充
     *
     * @param helper
     * @param item
     */
    @Override
    protected void convertHead(BaseViewHolder helper, final T item) {
        if (mBaseMultiGroupAdapterListener != null) {
            mBaseMultiGroupAdapterListener.convertHead(helper, item);
        }
    }

    /**
     * 处理分组内容填充（多个不同的类型，需要匹配初始化时的类型）
     *
     * @param helper
     * @param item
     */
    @Override
    protected void convert(BaseViewHolder helper, T item) {
        if (mBaseMultiGroupAdapterListener != null) {
            mBaseMultiGroupAdapterListener.convertItem(helper, item);
        }
    }
}
