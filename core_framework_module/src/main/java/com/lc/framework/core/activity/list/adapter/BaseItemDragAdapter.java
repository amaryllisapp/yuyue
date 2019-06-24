package com.lc.framework.core.activity.list.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.lc.framework.core.activity.list.listener.BaseAdapterListener;
import com.lc.framework.core.activity.list.listener.OnDragItemSwipeListener;

import java.util.List;

/**
 * 类名：BaseItemDragAdapter
 * 描述：通过拖动ITEM，来改变ITEM的位置
 * 应用场景：ITEM通过选中可以拖动进行改变位置或者删除
 *
 * e.g
 *              new BaseItemDragAdapter(new BaseAdapterListener())
 *                 .attach(new RecyclerView())
 *                 .enableSwipeItem(true)
 *                 .enableDragItem(true)
 *                 .swipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END)
 *                 .listener(new OnDragItemSwipeListener())
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/21 15:09
 */
public class BaseItemDragAdapter<T> extends BaseItemDraggableAdapter<T, BaseViewHolder> {

    private BaseAdapterListener mBaseAdapterListener;

    private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;

    private ItemTouchHelper mItemTouchHelper;

    public BaseItemDragAdapter(BaseAdapterListener baseAdapterListener) {
        this(baseAdapterListener, null);
    }

    public BaseItemDragAdapter(BaseAdapterListener baseAdapterListener, @Nullable List<T> data) {
        super(baseAdapterListener.itemLayoutId(), data);
        mBaseAdapterListener = baseAdapterListener;
        mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(this);
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        mBaseAdapterListener.convertItem(helper, item);
    }

    protected BaseItemDragAdapter enableSwipeItem(boolean enable) {
        if (enable) {
            enableSwipeItem();
        } else {
            disableSwipeItem();
        }
        return this;
    }

    protected BaseItemDragAdapter enableDragItem(boolean enable) {
        if (enable) {
            enableDragItem(mItemTouchHelper);
        } else {
            disableDragItem();
        }
        return this;
    }

    /**
     * 附加拖动的ITEM到RecyclerView中
     *
     * @param recyclerView
     */
    protected BaseItemDragAdapter attach(RecyclerView recyclerView) {
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        return this;
    }

    /**
     * 设置移动标记
     * ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN
     *
     * @param swipeMoveFlags
     */
    protected BaseItemDragAdapter swipeMoveFlags(int swipeMoveFlags) {
        if (swipeMoveFlags <= 0) {
            swipeMoveFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        }
        mItemDragAndSwipeCallback.setSwipeMoveFlags(swipeMoveFlags);
        return this;
    }

    /**
     * 设置监听器
     *
     * @param onItemSwipeListener
     * @return
     */
    protected BaseItemDragAdapter listener(OnDragItemSwipeListener onItemSwipeListener) {
        if (onItemSwipeListener != null) {
            setOnItemSwipeListener(onItemSwipeListener);
            setOnItemDragListener(onItemSwipeListener);
        }
        return this;
    }
}
