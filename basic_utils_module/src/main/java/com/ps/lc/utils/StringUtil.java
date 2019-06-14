/*
 * Copyright (c) 2015. SJY.JIANGSU Corporation. All rights reserved
 */

package com.ps.lc.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 类名：StringUtil
 * 描述：字符串处理，字符串与其他类型转换工具类
 *
 * @author liucheng - liucheng@xhg.com
 *
 * @date 2019/6/14 18:18
 */
public class StringUtil {

    public static boolean isEmpty(String aText) {
        if (aText == null) {
            return true;
        } else {
            return aText.trim().length() == 0;
        }
    }

    public static boolean isNotEmpty(String text) {
        return !isEmpty(text);
    }

    public static String parseString(Object obj) {
        return parseString(obj, "");
    }

    public static String nvl(String str) {
        return StringUtil.isEmpty(str) ? "" : str.trim();
    }

    public static String parseString(Object object, String defValue) {
        String result = defValue;
        if (object == null) {
            return result;
        }
        try {
            result = String.valueOf(object);
        } catch (Exception e) {
            Log.getStackTraceString(e);
            result = defValue;
        }
        return result;
    }

    public static int parseInt(String str) {
        return parseInt(str, 0);
    }

    public static int parseInt(String str, int defValue) {
        int result = defValue;
        if (str == null) {
            return result;
        }
        try {
            result = Integer.parseInt(str);
        } catch (Exception e) {
            Log.getStackTraceString(e);
            result = defValue;
        }
        return result;
    }

    public static long parseLong(String str) {
        return parseLong(str, 0);
    }

    public static long parseLong(String str, long defValue) {
        long result = defValue;
        if (str == null) {
            return result;
        }
        try {
            result = Long.parseLong(str);
        } catch (Exception e) {
            Log.getStackTraceString(e);
            result = defValue;
        }
        return result;
    }

    public static float parseFloat(String str) {
        return parseFloat(str, 0);
    }

    public static float parseFloat(String str, float defValue) {
        float result = defValue;
        if (str == null) {
            return result;
        }
        try {
            result = Float.parseFloat(str);
        } catch (Exception e) {
            Log.getStackTraceString(e);
            result = defValue;
        }
        return result;
    }

    public static double parseDouble(String str) {
        return parseDouble(str, 0);
    }

    public static double parseDouble(String str, double defValue) {
        double result = defValue;
        if (isEmpty(str)) {
            return result;
        }
        try {
            result = Double.parseDouble(str);
        } catch (Exception e) {
            Log.getStackTraceString(e);
            result = defValue;
        }
        return result;
    }

    public static boolean parseBoolean(String value) {
        return parseBoolean(value, false);
    }

    public static boolean parseBoolean(String value, boolean aDefault) {
        if (value == null) {
            return aDefault;
        }
        if ("1".equals(value) || "true".equalsIgnoreCase(value)) {
            return true;
        }
        return false;
    }

    public static String dateToString(long date) {
        final SimpleDateFormat repayTimeDate = new SimpleDateFormat("yyyy-MM");
        try {
            return repayTimeDate.format(date);
        } catch (Exception e) {
            return repayTimeDate.format(new Date());
        }
    }

    public static String stringToPartition(String str) {
        try {
            str = str.substring(0, 4) + "年" + str.substring(5, 7) + "月";
            return str;
        } catch (Exception e) {
            return str;
        }
    }

    public static String[] split(String original, String regex) {
        return split(original, regex, true);
    }

    public static String[] split(String aOriginal, String aRegex, boolean aCanNull) {
        if (isEmpty(aOriginal)) {
            return new String[0];
        }
        if (aRegex == null || aRegex.length() == 0) {
            return new String[]{aOriginal};
        }
        String[] sTarget;
        int sTargetLength = 0;
        int sLength = aOriginal.length();
        int sStartIndex = 0, sEndIndex;
        //扫描字符串，确定目标字符串数组的长度
        for (sEndIndex = aOriginal.indexOf(aRegex, 0); sEndIndex != -1 && sEndIndex < sLength;
             sEndIndex = aOriginal.indexOf(aRegex, sEndIndex)) {
            sTargetLength += (aCanNull || sStartIndex != sEndIndex) ? 1 : 0;
            sStartIndex = sEndIndex += sEndIndex >= 0 ? aRegex.length() : 0;
        }
        //如果最后一个标记的位置非字符串的结尾，则需要处理结束串
        sTargetLength += aCanNull || sStartIndex != sLength ? 1 : 0;
        //重置变量值，根据标记拆分字符串
        sTarget = new String[sTargetLength];
        int sIndex;
        for (sIndex = 0, sEndIndex = aOriginal.indexOf(aRegex, 0), sStartIndex = 0;
             sEndIndex != -1 && sEndIndex < sLength;
             sEndIndex = aOriginal.indexOf(aRegex, sEndIndex)) {
            if (aCanNull || sStartIndex != sEndIndex) {
                sTarget[sIndex] = aOriginal.substring(sStartIndex, sEndIndex);
                ++sIndex;
            }
            sStartIndex = sEndIndex += sEndIndex >= 0 ? aRegex.length() : 0;
        }
        //取结束的子串
        if (aCanNull || sStartIndex != sLength) {
            sTarget[sTargetLength - 1] = aOriginal.substring(sStartIndex);
        }
        return sTarget;
    }


    /**
     * 清除空格&-
     *
     * @param str
     * @return
     */
    public static String clearSpace(String str) {
        if (StringUtil.isNotEmpty(str)) {
            str = str.replace(" ", "").replace("-", "").replace("+", "").trim();
        }
        return str;
    }


    /**
     * 生成唯一标识码
     *
     * @param digit 需要生成的位数。
     * @return 返回标识码
     */
    public static String buildUId(int digit) {
        String all = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < digit; i++) {
            char c = all.charAt(new Random().nextInt(all.length()));
            buffer.append(c);
        }
        return buffer.toString();
    }


    /**
     * 去除空格、回车、换行符、制表符
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        dest = dest.replaceAll("[\\x00\\x0a\\x0d\\x1f\\x20]", " ");
        dest = dest.replaceAll("\\u0000", "");
        return dest;
    }
}
