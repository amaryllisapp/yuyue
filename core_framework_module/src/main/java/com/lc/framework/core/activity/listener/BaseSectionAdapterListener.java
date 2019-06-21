package com.lc.framework.core.activity.listener;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * 类名：com.lc.framework.core.activity.adapter
 * 描述：列表基本实现监听器
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/20 15:25
 */
public interface BaseSectionAdapterListener<T extends SectionEntity> extends BaseAdapterListener<T> {
    /**
     * 头部布局
     *
     * @return
     */
    int headerItemLayoutId();

    /**
     * 头部布局填充
     *
     * @param baseViewHolder
     * @param t
     */
    void convertHeaderItem(BaseViewHolder baseViewHolder, T t);
}
