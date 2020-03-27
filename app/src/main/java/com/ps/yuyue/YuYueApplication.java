package com.ps.yuyue;

import com.lc.framework.BaseApplication;

//import com.umeng.analytics.MobclickAgent;
//import com.umeng.commonsdk.UMConfigure;

/**
 * 类名：com.ps.yuyue
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/14 17:58
 */
public class YuYueApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
//        SobotHelper.onApplicationOnCreate(this);
        //初始化SDK,s为在猎鹰联盟开发者平台创建媒体时获得的 ID
//        FalconAdEntrance.getInstance().init(this, "2306");
//        initUmeng();
    }

    /*private void initUmeng(){
        //设置LOG开关，默认为false
//        UMConfigure.setLogEnabled(true);
        try {
            Class<?> aClass = Class.forName("com.umeng.commonsdk.UMConfigure");
            Field[] fs = aClass.getDeclaredFields();
            for (Field f:fs){
                Log.e("xxxxxx","ff="+f.getName()+"   "+f.getType().getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        UMConfigure.init(this,"5ddba0964ca357906b001114", "UMENG_CHANNEL1", UMConfigure.DEVICE_TYPE_PHONE, "");
        //支持多进程打点(自定义事件专用)
        UMConfigure.setProcessEvent(true);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }*/

}
