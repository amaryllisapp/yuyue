package com.ps.lc.utils.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.StringRes;
import android.support.v4.content.PermissionChecker;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.ps.lc.utils.ResourceUtil;
import com.ps.lc.utils.ToastUtil;

import java.util.List;

/**
 * 动态权限获取工具类
 *
 * @author 36077 - liucheng@xhg.com
 * @date 2018/10/11 16:43
 **/
public class PermissionUtil {
    /**
     * 文件读写权限
     */
    public static final int REQUEST_CODE_STORAGE = 0x01;
    /**
     * 短信
     */
    public static final int REQUEST_CODE_SMS = 0x02;
    /**
     * 传感器
     */
    public static final int REQUEST_CODE_SENSORS = 0x03;
    /**
     * 手机状态
     */
    public static final int REQUEST_CODE_PHONE = 0x04;
    /**
     * 通话记录权限组
     */
    public static final int REQUEST_CODE_MICROPHONE = 0x05;
    /**
     * 定位权限
     */
    public static final int REQUEST_CODE_LOCATION = 0x06;
    /**
     * 联系人权限状态
     */
    public static final int REQUEST_CODE_CONTACTS = 0x07;
    /**
     * 拍照权限
     */
    public static final int REQUEST_CODE_CAMERA = 0x08;
    /**
     * 日历权限
     */
    public static final int REQUEST_CODE_CALENDAR = 0x09;

    private static OnPermission callback = new OnPermission() {
        @Override
        public void hasPermission(List<String> granted, boolean isAll) {
            mPermissionCallBack.hasPermission(granted, isAll);
        }

        @Override
        public void noPermission(List<String> denied, boolean quick) {
            mPermissionCallBack.noPermission(denied, quick);
        }
    };

    private static PermissionCallback mPermissionCallBack;



    /**
     * 欢迎界面权限获取
     *
     * @param activity
     * @param permissionCallBack
     */
    public static void requestSplashPerission(final Activity activity, PermissionCallback permissionCallBack) {
        mPermissionCallBack = permissionCallBack;
        XXPermissions.with(activity)
                //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                .constantRequest()
                //不指定权限则自动获取清单中的危险权限
                .permission(Permission.Group.STORAGE, new String[]{Permission.READ_PHONE_STATE}, new String[]{Permission.ACCESS_FINE_LOCATION})
                .request(callback);
    }


    /**
     * 文件读写权限动态获取
     * <p>READ_EXTERNAL_STORAGE<p/>
     * <p>WRITE_EXTERNAL_STORAGE<p/>
     * 涉及以上权限，通过调用一次即可全部获取
     *
     * @param activity
     */
    public static void requestStoragePermission(Activity activity, PermissionCallback permissionCallBack) {
        mPermissionCallBack = permissionCallBack;
        XXPermissions.with(activity)
                //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
//                .constantRequest()
                //不指定权限则自动获取清单中的危险权限
                .permission(Permission.Group.STORAGE)
                .request(callback);
    }

    /**
     * 拍照权限动态获取
     * <p>CAMERA<p/>
     * <p>
     * 涉及以上权限，通过调用一次即可全部获取
     *
     * @param activity
     */
    public static void requestCameraPermission(Activity activity, PermissionCallback permissionCallBack) {
        mPermissionCallBack = permissionCallBack;
        XXPermissions.with(activity)
                //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
//                .constantRequest()
                //不指定权限则自动获取清单中的危险权限
                .permission(new String[]{Permission.CAMERA}, Permission.Group.STORAGE)
                .request(callback);

    }

    /**
     * 通话记录权限动态获取
     * <p>ACCESS_FINE_LOCATION<p/>
     * <p>ACCESS_COARSE_LOCATION<p/>
     * <p>
     * 涉及以上权限，通过调用一次即可全部获取
     *
     * @param activity
     */
    public static void requestLocationPermission(Activity activity, PermissionCallback permissionCallBack) {
        mPermissionCallBack = permissionCallBack;
        XXPermissions.with(activity)
                //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
//                .constantRequest()
                //不指定权限则自动获取清单中的危险权限
                .permission(Permission.ACCESS_FINE_LOCATION)
                .request(callback);
    }

    /**
     * 短信权限动态获取
     * <p>SEND_SMS<p/>
     * <p>RECEIVE_SMS<p/>
     * <p>READ_SMS<p/>
     * <p>RECEIVE_WAP_PUSH<p/>
     * <p>RECEIVE_MMS<p/>
     * <p>
     * 涉及以上权限，通过调用一次即可全部获取
     *
     * @param activity
     */
    public static void requestSmsPermission(Activity activity, PermissionCallback permissionCallBack) {
        mPermissionCallBack = permissionCallBack;
        XXPermissions.with(activity)
                //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
//                .constantRequest()
                //不指定权限则自动获取清单中的危险权限
                .permission(Permission.SEND_SMS, Permission.RECEIVE_SMS, Permission.READ_SMS, Permission.RECEIVE_WAP_PUSH, Permission.RECEIVE_MMS)
                .request(callback);
    }

    /**
     * 传感器权限动态获取
     * <p>BODY_SENSORS<p/>
     * <p>
     * 涉及以上权限，通过调用一次即可全部获取
     *
     * @param activity
     */
    public static void requestSensorsPermission(Activity activity, PermissionCallback permissionCallBack) {
        mPermissionCallBack = permissionCallBack;
        XXPermissions.with(activity)
                //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
//                .constantRequest()
                //不指定权限则自动获取清单中的危险权限
                .permission(Permission.BODY_SENSORS)
                .request(callback);
    }

    /**
     * 电话权限动态获取
     * <p>READ_PHONE_STATE<p/>
     * <p>CALL_PHONE<p/>
     * <p>READ_CALL_LOG<p/>
     * <p>WRITE_CALL_LOG<p/>
     * <p>ADD_VOICEMAIL<p/>
     * <p>USE_SIP<p/>
     * <p>PROCESS_OUTGOING_CALLS<p/>
     * <p>
     * 涉及以上权限，通过调用一次即可全部获取
     *
     * @param activity
     */
    public static void requestPhonePermission(Activity activity, PermissionCallback permissionCallBack) {
        mPermissionCallBack = permissionCallBack;
        XXPermissions.with(activity)
                //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
//                .constantRequest()
                //不指定权限则自动获取清单中的危险权限
                .permission(
                        Permission.READ_PHONE_STATE,
                        Permission.CALL_PHONE,
                        Permission.READ_CALL_LOG,
                        Permission.WRITE_CALL_LOG,
                        Permission.ADD_VOICEMAIL,
                        Permission.USE_SIP,
                        Permission.PROCESS_OUTGOING_CALLS
                )
                .request(callback);
    }


    /**
     * 通话记录权限动态获取
     * <p>RECORD_AUDIO<p/>
     * <p>
     * 涉及以上权限，通过调用一次即可全部获取
     *
     * @param activity
     */
    public static void requestMicroPhonePermission(Activity activity, PermissionCallback permissionCallBack) {
        mPermissionCallBack = permissionCallBack;
        XXPermissions.with(activity)
                //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
//                .constantRequest()
                //不指定权限则自动获取清单中的危险权限
                .permission(Permission.RECORD_AUDIO)
                .request(callback);
    }


    /**
     * 联系人权限动态获取
     * <p>READ_CONTACTS<p/>
     * <p>WRITE_CONTACTS<p/>
     * <p>GET_ACCOUNTS<p/>
     * <p>
     * 涉及以上权限，通过调用一次即可全部获取
     *
     * @param activity
     */
    public static void requestContactsPermission(Activity activity, PermissionCallback permissionCallBack) {
        mPermissionCallBack = permissionCallBack;
        XXPermissions.with(activity)
                //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
//                .constantRequest()
                //不指定权限则自动获取清单中的危险权限
                .permission(
                        Permission.Group.CONTACTS)
                .request(callback);
    }


    /**
     * 拍照权限动态获取
     * <p>READ_CALENDAR<p/>
     * <p>WRITE_CALENDAR<p/>
     * <p>
     * 涉及以上权限，通过调用一次即可全部获取
     *
     * @param activity
     */
    public static void requestCalendarPermission(Activity activity, PermissionCallback permissionCallBack) {
        mPermissionCallBack = permissionCallBack;
        XXPermissions.with(activity)
                //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
//                .constantRequest()
                //不指定权限则自动获取清单中的危险权限
                .permission(
                        Permission.Group.CALENDAR)
                .request(callback);
    }

    public static void showPermissionFailMsg(Context context, @StringRes int content){
        ToastUtil.showToast(context, ResourceUtil.getString(content));
    }


    /**
     * 检查是否存在该权限
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean checkPermissionGranted(Context context, String permission) {
        // For Android < Android M, self permissions are always granted.
        boolean result = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Integer.valueOf(android.os.Build.VERSION.SDK) >= Build.VERSION_CODES.M) {
                // targetSdkVersion >= Android M, we can
                // use Context#checkSelfPermission
                result = context.checkSelfPermission(permission)
                        == PackageManager.PERMISSION_GRANTED;
            } else {
                // targetSdkVersion < Android M, we have to use PermissionChecker
                result = PermissionChecker.checkSelfPermission(context, permission)
                        == PermissionChecker.PERMISSION_GRANTED;
            }
        }
        return result;
    }


    /**
     * 判断时候有权限
     *
     * @param context    context
     * @param permission permission
     * @return true yes false no
     */
    /*public static boolean doCheckPermissionIsPass(Context context, String permission) {
        if (StringUtil.isEmpty(permission)) {
            return false;
        }
        boolean result = false;
        int targetSdkVersion = 0;
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                // targetSdkVersion >= Android M, we can
                // use Context#checkSelfPermission
                result = context.checkSelfPermission(permission)
                        == PackageManager.PERMISSION_GRANTED;
            } else {
                // targetSdkVersion < Android M, we have to use PermissionChecker
                result = PermissionChecker.checkSelfPermission(context, permission)
                        == PermissionChecker.PERMISSION_GRANTED;
            }
        }
        return result;
    }*/


}
