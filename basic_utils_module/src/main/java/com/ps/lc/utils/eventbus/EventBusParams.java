package com.ps.lc.utils.eventbus;

/**
 * event bus 数据传输实体
 *
 * @author 36077 - liucheng@xhg.com
 * @date 2018/12/18 21:11
 **/
public class EventBusParams {
    /**
     * EVENT BUS KEY
     */
    public String key;
    /**
     * EVENT BUS VALUE
     */
    public Object object;

    public EventBusParams(String key, Object object) {
        this.key = key;
        this.object = object;
    }
}
