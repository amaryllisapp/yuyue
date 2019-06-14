package com.ps.lc.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.ps.lc.utils.exception.CustomException;
import com.ps.lc.utils.log.LogHelper;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class HardwareUtil {

    // SAFE_STATIC_VAR
    private static Context sContext = null;

    private static final boolean DEBUG = false;
    private static final String TAG = "HardwareUtil";

    public static final int LAYER_TYPE_NONE = 0;
    public static final int LAYER_TYPE_SOFTWARE = 1;
    public static final int LAYER_TYPE_HARDWARE = 2;

    private static final String CPU_INFO_CORE_COUNT_FILE_PATH = "/sys/devices/system/cpu/";

    private static final double COMPUTE_SCREEN_SIZE_DIFF_LIMIT = 0.5f;

    // SAFE_STATIC_VAR
    private static boolean sHasInitialAndroidId = false;
    // SAFE_STATIC_VAR
    private static String sAndroidId = "";
    // SAFE_STATIC_VAR
    private static boolean sHasInitMacAddress = false;
    // SAFE_STATIC_VAR
    private static String sMacAddress = "";
    // SAFE_STATIC_VAR
    private static boolean sHasInitIMEI = false, sHasInitIMSI = false;
    // SAFE_STATIC_VAR
    private static String sIMei = "", sIMsi = "";
    // SAFE_STATIC_VAR
    private static boolean sHasInitCpuCoreCount = false;
    // SAFE_STATIC_VAR
    private static int sCpuCoreCount = 1;
    // SAFE_STATIC_VAR
    private static boolean sHasInitDeviceSize = false;
    // SAFE_STATIC_VAR
    private static double sDeviceSize = 0;
    // SAFE_STATIC_VAR
    private static int sStatusBarHeight;
    // SAFE_STATIC_VAR
    private static boolean sHasCheckStatusBarHeight;

    /**
     * screenWidth & screenHeight means the display resolution. windowWidth &
     * windowHeight means the application window rectangle. in not full screen
     * mode: screenHeight == windowHeight + systemStatusBarHeight in full screen
     * mode: screenHeight == windowHeight
     * <p/>
     * no matter what situation, screenWidth === windowWidth;
     */
    // SAFE_STATIC_VAR
    private static int sScreenWidth = 0, sScreenHeight = 0;
    // SAFE_STATIC_VAR
    private static float sDensity = 1.0f, sDensityDpi = 240;

    public static int getScreenWidth() {
        return sScreenWidth;
    }

    public static void setScreenWidth(int sScreenWidth) {
        HardwareUtil.sScreenWidth = sScreenWidth;
    }

    public static int getScreenHeight() {
        return sScreenHeight;
    }

    public static void setScreenHeight(int sScreenHeight) {
        HardwareUtil.sScreenHeight = sScreenHeight;
    }

    public static float getDensity() {
        return sDensity;
    }

    public static void setDensity(float sDensity) {
        HardwareUtil.sDensity = sDensity;
    }

    public static float getDensityDpi() {
        return sDensityDpi;
    }

    public static void setDensityDpi(float sDensityDpi) {
        HardwareUtil.sDensityDpi = sDensityDpi;
    }

    /**
     * @param context
     * @note You must call this before calling any other methods!!
     */
    public static void init(Context context) {
        if (context != null) {
            sContext = context.getApplicationContext();
        }
    }

    /**
     * Call this to clear reference of Context instance, which is set by
     * {@link #init(Context)}.
     */
    public static void destroy() {
        sContext = null;
    }

    private static void checkIfContextInitialized() {
        if (sContext == null) {
            throw new CustomException(
                    "context has not been initialized! You MUST call this only after initialize() is invoked.");
        }
    }

    /**
     * @return A 64-bit number (as a hex string) that is randomly generated on
     * the device's first boot and should remain constant for the
     * lifetime of the device. (The value may change if a factory reset
     * is performed on the device
     */
    public static String getAndroidId() {
        checkIfContextInitialized();
        if (sHasInitialAndroidId) {
            return sAndroidId;
        }
        try {
            sAndroidId = Settings.Secure.getString(sContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        if (sAndroidId == null) {
            sAndroidId = "";
        }
        sHasInitialAndroidId = true;
        if (DEBUG) {
            Log.i(TAG, "getAndroidId: " + sAndroidId);
        }
        return sAndroidId;
    }

    /**
     * @return if get mac address onFailed, "" will be returned.
     */
    public static String getMacAddress() {
        checkIfContextInitialized();
        if (sHasInitMacAddress || sContext == null) {
            return sMacAddress;
        }
        try {
            WifiManager wifi = (WifiManager) sContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            sMacAddress = info.getMacAddress();
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        if (sMacAddress == null) {
            sMacAddress = "";
        } else if (!TextUtils.isEmpty(sMacAddress)) {
            sHasInitMacAddress = true;
        }
        if (DEBUG) {
            Log.i(TAG, "getMacAddress: " + sMacAddress);
        }
        return sMacAddress;
    }

    public static String getIMei() {
        checkIfContextInitialized();
        if (sHasInitIMEI || sContext == null) {
            return sIMei;
        }
        sIMei = getIMEIInner();
        if (TextUtils.isEmpty(sIMei)) {
            sIMei = "null";
        }
        sHasInitIMEI = true;
        return sIMei;
    }

    public static String getIMsi() {
        checkIfContextInitialized();
        if (sHasInitIMSI || sContext == null) {
            return sIMsi;
        }
        sIMsi = getIMSIInner();
        if (TextUtils.isEmpty(sIMsi)) {
            sIMsi = "null";
        }
        sHasInitIMSI = true;
        return sIMsi;
    }

    /**
     * 获取Sim卡no
     *
     * @return
     */
    public static String getSimNo() {
        try {
            TelephonyManager mTelephonyMgr = (TelephonyManager) sContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (ContextCompat.checkSelfPermission(sContext, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
                return mTelephonyMgr.getSimSerialNumber();
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        return "";
    }

    /**
     * @note make sure the READ_PHONE_STATE permission is opened in
     * AndroidManifest.xml if this method is used.<br>
     */
    private static TelephonyManager mTelephonyMgr;

    public static String getIMEIInner() {
        String iMei = null;
        try {
            if (mTelephonyMgr == null) {
                mTelephonyMgr = (TelephonyManager) sContext.getSystemService(Context.TELEPHONY_SERVICE);
            }
            if (ContextCompat.checkSelfPermission(sContext, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
                iMei = mTelephonyMgr.getDeviceId();
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        LogHelper.d("zenfer", " imei = " + iMei);
        return iMei;
    }

    public static String getIMSIInner() {
        String iMsi = null;
        try {
            if (mTelephonyMgr == null) {
                mTelephonyMgr = (TelephonyManager) sContext.getSystemService(Context.TELEPHONY_SERVICE);
            }
            if (ContextCompat.checkSelfPermission(sContext, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
                iMsi = mTelephonyMgr.getSubscriberId();
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        return iMsi;
    }

    public static int getCpuCoreCount() {
        if (sHasInitCpuCoreCount) {
            return sCpuCoreCount;
        }
        final class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                try {
                    if (Pattern.matches("cpu[0-9]+", pathname.getName())) {
                        return true;
                    }
                } catch (Exception e) {
                    Log.getStackTraceString(e);
                }
                return false;
            }
        }
        try {
            File dir = new File(CPU_INFO_CORE_COUNT_FILE_PATH);
            File[] files = dir.listFiles(new CpuFilter());
            sCpuCoreCount = files.length;
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }

        if (sCpuCoreCount < 1) {
            sCpuCoreCount = 1;
        }
        sHasInitCpuCoreCount = true;
        if (DEBUG) {
            Log.i(TAG, "getCpuCoreCount: " + sCpuCoreCount);
        }
        return sCpuCoreCount;
    }

    public static double getDeviceSize() {
        checkIfContextInitialized();
        // return HardwareUtilImpl.getDeviceSize(sContext);
        if (sHasInitDeviceSize || sContext == null) {
            return sDeviceSize;
        }
        final DisplayMetrics dm = new DisplayMetrics();
        final WindowManager wm = (WindowManager) sContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        final int width = guessSolutionValue(dm.widthPixels);
        final int height = guessSolutionValue(dm.heightPixels);
        final float dpi = dm.densityDpi;
        final float xdpi = dm.xdpi;
        final float ydpi = dm.ydpi;
        double screenSize = 0;
        if (Float.compare(dpi, 0) != 0) {
            screenSize = Math.sqrt((double) width * width + height * height) / dpi;
        }
        double screenSize2 = 0;
        if (Float.compare(xdpi, 0) != 0 && Float.compare(ydpi, 0) != 0) {
            double widthInches = width / xdpi;
            double heightInches = height / ydpi;
            screenSize2 = Math.sqrt(widthInches * widthInches + heightInches * heightInches);
        }
        final double diff = Math.abs(screenSize2 - screenSize);
        sDeviceSize = diff <= COMPUTE_SCREEN_SIZE_DIFF_LIMIT ? screenSize2 : screenSize;
        sHasInitDeviceSize = true;
        return sDeviceSize;
    }

    public static int getDeviceWidth() {
        return sScreenWidth < sScreenHeight ? sScreenWidth : sScreenHeight;
    }

    public static int getDeviceHeight() {
        return sScreenWidth > sScreenHeight ? sScreenWidth : sScreenHeight;
    }

    private static int guessSolutionValue(int value) {
        if (value >= 1180 && value <= 1280) {
            return 1280;
        }
        return value;
    }

    /**
     * <p>
     * Specifies the type of layer backing this view. The layer can be
     * {@link #LAYER_TYPE_NONE}, {@link #LAYER_TYPE_SOFTWARE} or
     * {@link #LAYER_TYPE_HARDWARE}.
     * </p>
     *
     * @param v
     * @param type The type of layer to use with this view, must be one of
     *             {@link #LAYER_TYPE_NONE}, {@link #LAYER_TYPE_SOFTWARE} or
     *             {@link #LAYER_TYPE_HARDWARE}
     * @see #LAYER_TYPE_NONE
     * @see #LAYER_TYPE_SOFTWARE
     * @see #LAYER_TYPE_HARDWARE
     */
    public static void setLayerType(View v, int type) {
        try {
            Integer realType;
            switch (type) {
                case LAYER_TYPE_NONE:
                    realType = ReflectionUtil.getIntFileValueFromClass(View.class, "LAYER_TYPE_NONE");
                    break;
                case LAYER_TYPE_SOFTWARE:
                    realType = ReflectionUtil.getIntFileValueFromClass(View.class, "LAYER_TYPE_SOFTWARE");
                    break;
                case LAYER_TYPE_HARDWARE:
                    realType = ReflectionUtil.getIntFileValueFromClass(View.class, "LAYER_TYPE_HARDWARE");
                    break;
                default:
                    throw new CustomException("unsupported layer type");
            }
            if (ReflectionUtil.INVALID_VALUE == realType) {
                return;
            }

            Class<View> cls = View.class;
            @SuppressWarnings("rawtypes")
            Class paramTypes[] = new Class[2];
            paramTypes[0] = Integer.TYPE;
            paramTypes[1] = Paint.class;
            Method method = cls.getMethod("setLayerType", paramTypes);
            Object argList[] = new Object[2];
            argList[0] = realType;
            argList[1] = null;
            method.invoke(v, argList);
        } catch (Exception ex) {
            Log.getStackTraceString(ex);
        }
    }

    public static void buildLayer(View v) {
        try {
            Class<View> cls = View.class;
            Method method = cls.getMethod("buildLayer", new Class[0]);
            method.invoke(v, new Object[0]);
        } catch (Exception ex) {
            Log.getStackTraceString(ex);
        }
    }
}