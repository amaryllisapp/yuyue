package com.ps.lc.net;

import com.ps.lc.net.callback.NetWorkCallback;

/**
 * Created by zhangwulin on 2017/1/3.
 * Email:zhangwulin@feitaikeji.com
 */

public interface INetwork {

    /**
     * 连网
     */
    void execute(NetWorkCallback callBack);

    /**
     * 取消连网
     */
    void cancel();
}
