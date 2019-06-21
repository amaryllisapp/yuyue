package com.lc.framework.core.activity.listener;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * 类名：com.lc.framework.core.activity.adapter
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/20 15:25
 */
public interface BaseMultiAdapterListener<T extends MultiItemEntity> extends BaseAdapterListener<T> {
    /**
     * ITEM对应布局集合(与itemsType的数据需要保持一致，为一一对应关系)
     *
     * @return
     */
    List<Integer> itemsLayout();

    /**
     * ITEM对应类型(与itemsLayout的数据需要保持一致，为一一对应关系)
     *
     * @return
     */
    List<Integer> itemsType();
}
