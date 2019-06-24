package com.lc.framework.core.activity.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.lc.framework.core.activity.listener.BaseSectionAdapterListener;

import java.util.List;

/**
 *  
 * 类名：BaseItemHeaderAdapter
 * 描述：基础列表的适配器
 *
 * @author liucheng - liucheng@xhg.com
 *
 * @date 2019/6/24 10:30
 */
public class BaseItemHeaderAdapter<T extends SectionEntity> extends BaseSectionQuickAdapter<T, BaseViewHolder> {

    private BaseSectionAdapterListener mBaseSectionAdapterListener;

    /**
     * @param baseSectionAdapterListener 适配器回调
     * @param data                       A new list is created out of this one to avoid mutable list
     */
    public BaseItemHeaderAdapter(BaseSectionAdapterListener baseSectionAdapterListener, @Nullable List data) {
        super(baseSectionAdapterListener.itemLayoutId(), baseSectionAdapterListener.headerItemLayoutId(), data);
    }

    /**
     * 填充头部布局
     *
     * @param helper
     * @param item
     */
    @Override
    protected void convertHead(BaseViewHolder helper, final T item) {
        mBaseSectionAdapterListener.convertHeaderItem(helper, item);
    }

    /**
     * 填充子类布局
     *
     * @param helper
     * @param item
     */
    @Override
    protected void convert(BaseViewHolder helper, T item) {
        mBaseSectionAdapterListener.convertItem(helper, item);
    }
}
