package com.ps.lc.net.certificate;

import android.content.Context;

/**
 * Created by zhangwulin on 2016/12/26.
 * Email:zhangwulin@feitaikeji.com
 */
public class HttpsCertificate {
    private String sCertificate;
    private String sPwd;
    private String cCertificate;
    private String cPwd;
    private Context mContext;

    public HttpsCertificate(Context context) {
        super();
        mContext = context;
    }

    public HttpsCertificate(Context context, String sCertificate, String sPwd, String cCertificate, String cPwd) {
        this.sCertificate = sCertificate;
        this.sPwd = sPwd;
        this.cCertificate = cCertificate;
        this.cPwd = cPwd;
        mContext = context;
    }

    public String getSCertificate() {
        return sCertificate;
    }

    public void setSCertificate(String sCertificate) {
        this.sCertificate = sCertificate;
    }

    public String getSPwd() {
        return sPwd;
    }

    public void setSPwd(String sPwd) {
        this.sPwd = sPwd;
    }

    public String getCCertificate() {
        return cCertificate;
    }

    public void setCCertificate(String cCertificate) {
        this.cCertificate = cCertificate;
    }

    public String getCPwd() {
        return cPwd;
    }

    public void setCPwd(String cPwd) {
        this.cPwd = cPwd;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }
}
