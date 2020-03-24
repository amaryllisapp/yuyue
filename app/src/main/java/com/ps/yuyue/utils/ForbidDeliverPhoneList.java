package com.ps.yuyue.utils;

import java.util.List;

/**
 * 禁止投递手机号列表
 */
public class ForbidDeliverPhoneList {
    /**
     * 后台返回的Gzip压缩内容，默认以字符串形式存在
     */
    private String phoneGzip;

    private List<String> phoneList;

    public ForbidDeliverPhoneList() {
    }

    public List<String> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<String> phoneList) {
        this.phoneList = phoneList;
    }

    public String getPhoneGzip() {
        return phoneGzip;
    }

    public void setPhoneGzip(String phoneGzip) {
        this.phoneGzip = phoneGzip;
    }
}