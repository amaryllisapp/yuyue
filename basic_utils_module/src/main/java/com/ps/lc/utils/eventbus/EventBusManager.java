package com.ps.lc.utils.eventbus;


import com.ps.lc.utils.log.LogHelper;

import org.greenrobot.eventbus.EventBus;

import android.support.annotation.NonNull;


/**
 * Event Bus 事件操作管理类
 * <p>
 * 使用过程注意:
 * 1. 注册一定在所有组件初始化之后，否则会出问题
 * 2. @Subscribe(threadMode = ThreadMode.MAIN)方法之前一定要有注册操作。同理，注册之后，一定要有该注解及对应的方法，否则报错
 * 3. 每个页面销毁时，一定要记得销毁，否则会出现内存泄露问题
 *
 * @author 36077 - liucheng@xhg.com
 * @date 2018/12/18 17:50
 **/
public class EventBusManager {
    /**
     * EventBUS注册
     *
     * @param object
     */
    public static void register(@NonNull Object object) {
        if (isExits() && !isRegistered(object)) {
            LogHelper.i("EventBusManager - register", "注册Eventbus成功");
            EventBus.getDefault().register(object);
        }
    }

    /**
     * Event解绑
     *
     * @param object
     */
    public static void unRegister(@NonNull Object object) {
        if (isExits() && isRegistered(object)) {
            LogHelper.i("EventBusManager - register", "解除注册Eventbus成功");
            EventBus.getDefault().unregister(object);
        }
    }

    /**
     * 发送EventBus数据（无值）
     *
     * @param key
     */
    public static void post(@NonNull String key) {
        if (isExits()) {
            LogHelper.i("EventBusManager - register", "推送Eventbus成功（空值）");
            EventBus.getDefault().post(new EventBusParams(key, null));
        }
    }

    /**
     * 发送EventBus数据（有值）
     *
     * @param key
     * @param value
     */
    public static void post(@NonNull String key, @NonNull Object value) {
        if (isExits()) {
            LogHelper.i("EventBusManager - register", "推送Eventbus成功(有值)");
            EventBus.getDefault().post(new EventBusParams(key, value));
        }
    }

    public static boolean isExits() {
        try {
            return EventBus.getDefault() != null;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 对象是否被注册
     *
     * @param obj
     * @return
     */
    public static boolean isRegistered(Object obj) {
        return EventBus.getDefault().isRegistered(obj);
    }
}
