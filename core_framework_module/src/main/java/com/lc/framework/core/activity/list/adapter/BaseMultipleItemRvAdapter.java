package com.lc.framework.core.activity.list.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.lc.framework.core.activity.list.listener.BaseMultipleItemRvListener;

import java.util.List;

/**
 * 类名：BaseMultipleItemRvAdapter
 * 描述：列表多类型布局基础基础适配器，应用于SimpleListActivity及其同级别业务抽象实现或具体实现
 * 依赖：BaseRecyclerViewAdapterHelper
 * 应用场景：及其列表复杂布局类型【几种到几十种列表类型】，聊天页面等
 * 原理：采用委托代理模式，业务只需要将数据传入并确定好具体的类型，底层将根据类型委派到具体Provider进行数据状态，布局装载以及显示。
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/21 10:33
 */
public class BaseMultipleItemRvAdapter<T extends MultiItemEntity> extends MultipleItemRvAdapter<T, BaseViewHolder> {

    private BaseMultipleItemRvListener mBaseMultipleItemRvListener;

    public BaseMultipleItemRvAdapter(BaseMultipleItemRvListener baseMultipleItemRvListener, @Nullable List<T> data) {
        super(data);
        mBaseMultipleItemRvListener = baseMultipleItemRvListener;
        //构造函数若有传其他参数可以在调用finishInitialize()之前进行赋值，赋值给全局变量
        //这样getViewType()和registerItemProvider()方法中可以获取到传过来的值
        //getViewType()中可能因为某些业务逻辑，需要将某个值传递过来进行判断，返回对应的viewType
        //registerItemProvider()中可以将值传递给ItemProvider

        //If the constructor has other parameters, it needs to be assigned before calling finishInitialize() and assigned to the global variable
        // This getViewType () and registerItemProvider () method can get the value passed over
        // getViewType () may be due to some business logic, you need to pass a value to judge, return the corresponding viewType
        //RegisterItemProvider() can pass value to ItemProvider

        finishInitialize();
    }

    @Override
    protected int getViewType(T entity) {
        //根据实体类判断并返回对应的viewType，具体判断逻辑因业务不同，这里这是简单通过判断type属性
        //According to the entity class to determine and return the corresponding viewType,
        //the specific judgment logic is different because of the business, here is simply by judging the type attribute
        return mBaseMultipleItemRvListener.getViewType(entity);
    }

    @Override
    public void registerItemProvider() {
        List<BaseItemProvider> itemProviders = mBaseMultipleItemRvListener.getItemProvider();
        //注册相关的条目provider
        //Register related entries provider

        for (BaseItemProvider itemProvider : itemProviders) {
            mProviderDelegate.registerProvider(itemProvider);
        }
    }
}
