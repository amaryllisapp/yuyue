package com.ps.yuyue;

import com.lc.framework.BaseApplication;
import com.ps.yuyue.sobot.SobotHelper;

/**
 * 类名：com.ps.yuyue
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/14 17:58
 */
public class YuYueApplication extends BaseApplication {

    @Override
    public void onCreate(){
        super.onCreate();
        SobotHelper.onApplicationOnCreate(this);
        //初始化SDK,s为在猎鹰联盟开发者平台创建媒体时获得的 ID
//        FalconAdEntrance.getInstance().init(this, "2306");
    }

}
