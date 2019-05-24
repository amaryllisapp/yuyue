package com.ps.lc.utils.dpcreator;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *  回收机 - 箱子满箱状态类型
 *
 *  @author 36077 - liucheng@xhg.com
 *  @date 2018/12/7 11:00
 *
 **/
@IntDef({
        FormatDirType.ALL,    // 所有都清除
        FormatDirType.MEGER,  // 只清除Dimens.xml文件
})
@Retention(RetentionPolicy.SOURCE)
public @interface FormatDirType {
    /**
     * 未满
     */
    int ALL = 1;
    /**
     * 满箱
     */
    int MEGER = 2;
}
