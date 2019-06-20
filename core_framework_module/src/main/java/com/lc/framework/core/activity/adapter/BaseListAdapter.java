package com.lc.framework.core.activity.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lc.framework.core.activity.listener.BaseAdapterListener;

/**
 * 类名：com.ps.yuyue.adapter
 * 描述：
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
