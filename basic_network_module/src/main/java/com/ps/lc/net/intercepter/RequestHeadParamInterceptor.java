package com.ps.lc.net.intercepter;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 类名：RequestHeadParamInterceptor
 * 描述：请求头参数(所有的动态请求参数均可以在该实现类中实现)
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/26 19:33
 */
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
