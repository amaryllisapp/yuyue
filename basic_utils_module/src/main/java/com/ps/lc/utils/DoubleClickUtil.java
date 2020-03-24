package com.ps.lc.utils;

/**
 * Created by zhangwulin on 2016/10/14.
 * Email:zhangwulin@feitaikeji.com
 */
public class DoubleClickUtil {

    private static DoubleClickUtil sInstance;
    private long mLastClickTime;
    private String mLastTag;

    private DoubleClickUtil() {
    }

    public static DoubleClickUtil getInstance() {
        if (sInstance == null) {
            sInstance = new DoubleClickUtil();
        }
        return sInstance;
    }

    /**
     * 是否可以点击
     * @return true 可以,反之不可
     */
    public boolean enableClick() {
       return enableClick(800);
    }

    public boolean enableClick(int time) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastClickTime > time) {
            mLastClickTime = currentTime;
            return true;
        }
        return false;
    }

    public boolean enableClick(String tag, int time) {
        if (tag != null && !tag.equals(mLastTag)) {
            mLastClickTime = 0;
        }
        mLastTag = tag;
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastClickTime > time) {
            mLastClickTime = currentTime;
            return true;
        }
        return false;
    }

    public void clearTag() {
        mLastTag = null;
    }
}
