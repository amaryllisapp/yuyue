package com.lc.framework.core.activity.list.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lc.framework.core.activity.list.listener.BaseAdapterListener;

/**
 * 类名：com.ps.yuyue.adapter
 * <p>
 * 描述：在集成SimpleListActivity情况下，业务调用只需要在页面实现itemLayoutId方法和convertItem方法进行数据装载即可.而不需要关注列表采用什么方式加载。如自定义列表，也可直接使用
 * 依赖：BaseRecyclerViewAdapterHelper
 * 应用场景：单类型列表布局
 * 原理：采用对象适配原则，SimpleListActivity底层默认采用该适配器
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/20 15:21
 */
public class BaseListAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    private BaseAdapterListener mBaseAdapterListener;

    public BaseListAdapter(BaseAdapterListener baseAdapterListener) {
        super(baseAdapterListener.itemLayoutId());
        mBaseAdapterListener = baseAdapterListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        mBaseAdapterListener.convertItem(helper, item);
    }
}
