package com.lc.framework.core.activity.listener;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.provider.BaseItemProvider;

import java.util.List;

/**
 * 类名：com.lc.framework.core.activity.adapter
 * 描述：多类型构建监听器
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/20 15:25
 */
public interface BaseMultipleItemRvListener<T extends MultiItemEntity> extends BaseAdapterListener<T> {
    /**
     * 获取 BaseItemProvider（与对应的类型匹配）
     *
     * @return
     */
    List<BaseItemProvider> getItemProvider();

    /**
     * 子类执行时调用该方法获取对应实体的类型
     * 扩展实现，需要根据实体返回与BaseItemProvider对应的类型
     * @param entity
     * @return
     */
    int getViewType(T entity);
}
