package com.lc.framework.core.activity.list.listener;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * 类名：com.lc.framework.core.activity.list.adapter
 * 描述：列表基本实现监听器
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/20 15:25
 */
public interface BaseAdapterListener<T> {
    /**
     * 子类布局
     * @return
     */
    int itemLayoutId();

    /**
     * 子类布局填充
     * @param baseViewHolder
     * @param t
     */
    void convertItem(BaseViewHolder baseViewHolder, T t);
}
