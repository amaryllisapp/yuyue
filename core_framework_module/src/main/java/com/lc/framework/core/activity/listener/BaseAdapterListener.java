package com.lc.framework.core.activity.listener;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * 类名：com.lc.framework.core.activity.adapter
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/20 15:25
 */
public interface BaseAdapterListener<T> {

    int itemLayoutId();

    void convertItem(BaseViewHolder baseViewHolder, T t);
}
