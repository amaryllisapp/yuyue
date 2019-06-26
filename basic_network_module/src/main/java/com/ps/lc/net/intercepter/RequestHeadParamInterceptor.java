package com.ps.lc.net.intercepter;


import com.ps.lc.utils.HardwareUtil;
import com.ps.lc.utils.StringUtil;
import com.ps.lc.utils.log.LogHelper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 请求头参数
 *
 * @author 36077 - liucheng@xhg.com
 * @date 2018/12/10 11:17
 **/
public class RequestHeadParamInterceptor extends ReqParamsInterceptor {
    /**
     * 请求头开关
     */
    private final boolean isOpenHttpHead = true;

    public RequestHeadParamInterceptor() {
        super();
    }

    @Override
    public Map<String, String> initHeaderParamsMap() {
        return getHeadParam();
    }

    @Override
    public Map<String, String> initParamsMap() {
        return null;
    }

    private Map<String, String> getHeadParam() {
        if (!isOpenHttpHead) {
            return null;
        }
        Map<String, String> mHead = new LinkedHashMap<>();
        /*mHead.put("token", TokenUtil.getToken() + "");
        mHead.put("deviceId", SystemUtil.getDeviceInfo() == null ? "" : SystemUtil.getDeviceInfo().deviceId + "");
        mHead.put("appId", SystemUtil.getAppInfo() == null ? "" : SystemUtil.getAppInfo().packageName + "");
        mHead.put("appVersion", SystemUtil.getAppInfo() == null ? "" : SystemUtil.getAppInfo().versionName + "");
        mHead.put("configVersion", APPConfig.getConfigVersion() + "");
        mHead.put("ostype", "ANDROID");
        mHead.put("channel", APPConfig.getChannel() + "");
        mHead.put("phoneModel", android.os.Build.MODEL + "");
        mHead.put("phoneResolution", HardwareUtil.getScreenWidth() + "*" + HardwareUtil.getScreenHeight() + "");
        mHead.put("systemVersion", android.os.Build.VERSION.RELEASE + "");
        mHead.put("validateTime", StringUtil.parseString(System.currentTimeMillis()) + "");
        mHead.put("clientName", "xhg_appclient_b");
        LogHelper.i("http-head", mHead.toString());*/
        return mHead;
    }
}
