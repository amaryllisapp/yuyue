package com.ps.lc.utils.permission;

import com.hjq.permissions.OnPermission;

import java.util.List;

/**
 * Created by zhangwulin on 2018/1/27.
 * zhangwulin@feitaikeji.com
 */

public interface PermissionCallback{
    /**
     * 有权限被授予时回调
     *
     * @param granted           请求成功的权限组
     * @param isAll             是否全部授予了
     */
    void hasPermission(List<String> granted, boolean isAll);

    /**
     * 有权限被拒绝授予时回调
     *
     * @param denied            请求失败的权限组
     * @param quick             是否有某个权限被永久拒绝了
     */
    void noPermission(List<String> denied, boolean quick);
}
