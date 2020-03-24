package com.lc.framework.core.activity.list.listener;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 类名：com.lc.framework.core.activity.list.adapter
 * 描述：分组展示不同样式的列表监听器
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/20 15:25
 */
public interface BaseMultiGroupAdapterListener<T extends MultiItemEntity> extends BaseMultiAdapterListener<T> {
    /**
     * 填充不同组对应的Header
     *
     * @return
     */
    void convertHead(BaseViewHolder helper, final T item);
}
