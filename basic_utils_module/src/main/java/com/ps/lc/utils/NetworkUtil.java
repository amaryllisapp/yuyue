package com.ps.lc.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by substring on 2016/8/1.
 * Email：zhangxuan@feitaikeji.com
 */
public class NetworkUtil {

    /**
     * 网络类型是wifi
     */
    public static final String NETWORK_TYPE_WIFI = "wifi";
    public static final String NETWORK_TYPE_NONE = "no_network";
    public static final String NETWORK_TYPE_UNKNOWN = "unknown";

    //接入点类型
    public final static int NETWORK_AP_TYPE_MOBILE_WAP = 0;     //cmwap
    public final static int NETWORK_AP_TYPE_MOBILE_NET = 1;     //cmnet
    public final static int NETWORK_AP_TYPE_WIFI = 2;         //wifi
    public final static int NETWORK_AP_TYPE_NOT_CONFIRM = 99;   //not confirm

    public static final int NETWORK_ERROR = 0;
    public static final int NETWORK_2G = 1;
    public static final int NETWORK_3G4G = 2;
    public static final int NETWORK_WIFI = 3;

    /*
     * 接入点类型：
     * 0：未知(默认)
     * 1：2G
     * 2：2.5G
     * 3：2.75G
     * 4：3G
     * 5：wifi接入点
     * 6：4G
     * */
    public static final int AP_UNKNOWN = 0;
    public static final int AP_2G = 1;
    public static final int AP_2_5G = 2;
    public static final int AP_2_75G = 3;
    public static final int AP_3G = 4;
    public static final int AP_WIFI = 5;
    public static final int AP_4G = 6;

    public static final String NETWORK_CLASS_NO_NETWORK = "-1";
    public static final String NETWORK_CLASS_UNKNOWN = "0";
    public static final String NETWORK_CLASS_2G = "2G";
    public static final String NETWORK_CLASS_2_5G = "2.5G";
    public static final String NETWORK_CLASS_2_75G = "2.75G";
    public static final String NETWORK_CLASS_3G = "3G";
    public static final String NETWORK_CLASS_4G = "4G";

    // 这部分在低版本的SDK中没有定义,但高版本有定义,
    // 由于取值不与其它常量不冲突,因此直接在这里定义
    private static final int NETWORK_TYPE_EVDO_B = 12;
    private static final int NETWORK_TYPE_EHRPD = 14;
    private static final int NETWORK_TYPE_HSPAP = 15;
    private static final int NETWORK_TYPE_LTE = 13;

    private static boolean sCacheActiveNetworkInitial = false;
    private static NetworkInfo sCacheActiveNetwork = null;

    /**
     * @see {@link #NETWORK_2G}, {@link #NETWORK_3G4G}, {@link #NETWORK_WIFI}, {@link #NETWORK_ERROR}
     */
    public static int getNetworkType() {
        if (NetworkUtil.isWifiNetwork()) {
            return NETWORK_WIFI;
        }
        if (NetworkUtil.is2GNetwork()) {
            return NETWORK_2G;
        } else if (NetworkUtil.is3GAboveNetwork()) {
            return NETWORK_3G4G;
        }
        return NETWORK_ERROR;
    }

    public static boolean isWifiNetwork() {
        if (mNetworkChangedNetworkArgs != null) {
            synchronized (NetworkUtil.class) {
                if (mNetworkChangedNetworkArgs != null) {
                    return mNetworkChangedNetworkArgs.mIsWifi;
                }
            }
        }
        String apnName = getAccessPointName();
        return NETWORK_TYPE_WIFI.equals(apnName);
    }


    /**
     * 是否是移动网络
     *
     * @return
     */
    public static boolean isMobileNetwork() {
        if (mNetworkChangedNetworkArgs != null) {
            synchronized (NetworkUtil.class) {
                if (mNetworkChangedNetworkArgs != null) {
                    return mNetworkChangedNetworkArgs.mIsMobileNetwork;
                }
            }
        }
        String apnName = getAccessPointName();
        return (!NETWORK_TYPE_WIFI.equals(apnName)) && (!NETWORK_TYPE_UNKNOWN.equals(apnName)) && (!NETWORK_TYPE_NONE.equals(apnName));
    }

    public static boolean is2GNetwork() {
        String networkClass = getNetworkClass();
        if (NETWORK_CLASS_2G.equals(networkClass)
                || NETWORK_CLASS_2_5G.equals(networkClass)
                || NETWORK_CLASS_2_75G.equals(networkClass)) {
            return true;
        }
        return false;
    }

    /**
     * 是否是3G或以上的移动网络
     *
     * @return
     */
    public static boolean is3GAboveNetwork() {
        String networkClass = getNetworkClass();
        if (NETWORK_CLASS_NO_NETWORK.equals(networkClass) || NETWORK_CLASS_UNKNOWN.equals(networkClass) ||
                NETWORK_CLASS_2G.equals(networkClass)
                || NETWORK_CLASS_2_5G.equals(networkClass)
                || NETWORK_CLASS_2_75G.equals(networkClass)) {
            return false;
        }
        return true;
    }

    /**
     * 获取接入点
     *
     * @return cmwap:0 cmnet:1 wfi:2: 99
     */
    public static int getCurrAccessPointType() {
        if (mNetworkChangedNetworkArgs != null) {
            synchronized (NetworkUtil.class) {
                if (mNetworkChangedNetworkArgs != null) {
                    return mNetworkChangedNetworkArgs.mCurrAccessPointType;
                }
            }
        }
        String apnName = NetworkUtil.getAccessPointName();
        if (NetworkUtil.NETWORK_CLASS_NO_NETWORK.equals(apnName) || NetworkUtil.NETWORK_CLASS_UNKNOWN.equals(apnName)) {
            return NETWORK_AP_TYPE_NOT_CONFIRM;
        }
        if (NetworkUtil.NETWORK_TYPE_WIFI.equalsIgnoreCase(apnName)) {
            return NETWORK_AP_TYPE_WIFI;
        }
        return (NetworkUtil.hasProxyForCurApn() ? NETWORK_AP_TYPE_MOBILE_WAP : NETWORK_AP_TYPE_MOBILE_NET);
    }

    public static boolean isNetworkConnected() {
        if (mNetworkChangedNetworkArgs != null) {
            synchronized (NetworkUtil.class) {
                if (mNetworkChangedNetworkArgs != null) {
                    return mNetworkChangedNetworkArgs.mIsConnected;
                }
            }
        }
        NetworkInfo info = getActiveNetworkInfo();
        return null != info && info.isConnected();
    }

    /**
     * 获得当前使用网络的信息<br/>
     * 即是连接的网络，如果用系统的api得到的activeNetwork为null<br/>
     * 我们还会一个个去找，以适配一些机型上的问题
     */
    public static NetworkInfo getActiveNetworkInfo() {
        return doGetActiveNetworkInfo(false);
    }

    public static NetworkInfo getActiveNetworkInfoFromCache() {
        return doGetActiveNetworkInfo(true);
    }

    private static NetworkInfo doGetActiveNetworkInfo(boolean isGetFromCache) {
        if (mNetworkChangedNetworkArgs != null) {
            synchronized (NetworkUtil.class) {
                if (mNetworkChangedNetworkArgs != null) {
                    return mNetworkChangedNetworkArgs.mActiveNetworkInfo;
                }
            }
        }
        if (isGetFromCache) {
            if (sCacheActiveNetworkInitial) {
                return sCacheActiveNetwork;
            }
        }
        NetworkInfo activeNetwork = null;
        try {
            ConnectivityManager cm = (ConnectivityManager) AppContextUtil.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) {
                return null;
            }
            activeNetwork = cm.getActiveNetworkInfo(); /*获得当前使用网络的信息*/
            if (activeNetwork == null || !activeNetwork.isConnected()) {//当前无可用连接,或者没有连接,尝试取所有网络再进行判断一次
                NetworkInfo[] allNetworks = cm.getAllNetworkInfo();//取得所有网络
                if (allNetworks != null) {//网络s不为null
                    for (int i = 0; i < allNetworks.length; i++) {//遍历每个网络
                        if (allNetworks[i] != null) {
                            if (allNetworks[i].isConnected()) {//此网络是连接的，可用的
                                activeNetwork = allNetworks[i];
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        sCacheActiveNetwork = activeNetwork;
        if (!sCacheActiveNetworkInitial) {
            sCacheActiveNetworkInitial = true;
        }
        return activeNetwork;
    }

    public static String getProxyHost() {
        String proxyHost = null;
        // Proxy
        if (Build.VERSION.SDK_INT >= 11) {
            // Build.VERSION_CODES.ICE_CREAM_SANDWICH IS_ICS_OR_LATER
            proxyHost = System.getProperty("http.proxyHost");
        } else {
            Context context = AppContextUtil.getAppContext();
            if (context == null) {
                return proxyHost;
            }
            proxyHost = android.net.Proxy.getHost(context);
            // wifi proxy is unreachable in Android2.3 or lower version
            if (isWifiNetwork() && proxyHost != null && proxyHost.indexOf("10.0.0") != -1) {
                proxyHost = "";
            }
        }
        return proxyHost;
    }

    public static boolean hasProxyForCurApn() {
        Context context = AppContextUtil.getAppContext();
        if (context == null) {
            return false;
        }
        try {
            if (null == getProxyHost()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static String getAccessPointName() {
        return doGetAccessPointName(false);
    }

    private static String doGetAccessPointName(boolean isGetFromCache) {
        if (mNetworkChangedNetworkArgs != null) {
            synchronized (NetworkUtil.class) {
                if (mNetworkChangedNetworkArgs != null) {
                    return mNetworkChangedNetworkArgs.mAccessPointName;
                }
            }
        }
        NetworkInfo activeNetwork;
        if (isGetFromCache) {
            activeNetwork = getActiveNetworkInfoFromCache();
        } else {
            activeNetwork = getActiveNetworkInfo();
        }
        String apnName = NETWORK_TYPE_UNKNOWN;
        if (null == activeNetwork) {
            apnName = NETWORK_TYPE_NONE;
            return apnName;
        }
        int networkType = activeNetwork.getType();
        if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
            return NETWORK_TYPE_WIFI;
        }
        if (activeNetwork.getExtraInfo() != null) {
            apnName = activeNetwork.getExtraInfo().toLowerCase();
        }
        if (networkType == ConnectivityManager.TYPE_MOBILE) {
            if (apnName.contains("cmwap")) {
                apnName = "cmwap";
            } else if (apnName.contains("cmnet")) {
                apnName = "cmnet";
            } else if (apnName.contains("uniwap")) {
                apnName = "uniwap";
            } else if (apnName.contains("uninet")) {
                apnName = "uninet";
            } else if (apnName.contains("3gwap")) {
                apnName = "3gwap";
            } else if (apnName.contains("3gnet")) {
                apnName = "3gnet";
            } else if (apnName.contains("ctwap")) {
                apnName = "ctwap";
            } else if (apnName.contains("ctnet")) {
                apnName = "ctnet";
            } else {

            }
        } else {
            apnName = NETWORK_TYPE_WIFI;
        }
        return apnName;
    }

    public static String getNetworkClass() {
        NetworkInfo activeNetwork = getActiveNetworkInfo();
        if (activeNetwork == null) {
            return NETWORK_CLASS_NO_NETWORK;
        }

        int netSubType = activeNetwork.getSubtype();
        switch (netSubType) {
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NETWORK_CLASS_2G;
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return NETWORK_CLASS_2_5G;
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return NETWORK_CLASS_2_75G;
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case NETWORK_TYPE_EVDO_B:
            case NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case NETWORK_TYPE_HSPAP:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return NETWORK_CLASS_3G;
            case NETWORK_TYPE_LTE:
                return NETWORK_CLASS_4G;
            default:
                return NETWORK_CLASS_UNKNOWN;
        }
    }

    //防止用户在手机网络状态发生改变时候获取网络参数导致的Binder IPC卡死问题
    public static class NetworkArgs {
        public NetworkInfo mActiveNetworkInfo;
        public boolean mIsWifi;
        public boolean mIsConnected;
        public int mCurrAccessPointType;
        public boolean mIsMobileNetwork;
        public String mAccessPointName;
    }

    private static NetworkArgs mNetworkChangedNetworkArgs;

    public static NetworkArgs getNetworkArgs() {
        return mNetworkChangedNetworkArgs;
    }

    public static void setNetworkArgs(NetworkArgs args) {
        synchronized (NetworkUtil.class) {
            mNetworkChangedNetworkArgs = args;
        }

        if (args != null) {
            sCacheActiveNetwork = args.mActiveNetworkInfo;
        }
    }
}
