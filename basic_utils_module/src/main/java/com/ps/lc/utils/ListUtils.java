package com.ps.lc.utils;

import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 类名：com.code.tool.utilsmodule.util
 * 描述：List 工具类
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/3/14 21:10
 */
public class ListUtils {
    /**
     * List集合不为空判断
     *
     * @param list
     * @return
     */
    public static <T> boolean isNotEmpty(List<T> list) {
        return list != null && list.size() > 0;
    }

    /**
     * List结合为空判断
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.size() <= 0;
    }

    /**
     * 获取两个集合的交集
     *
     * @param masterList
     * @param branchList
     * @return
     */
    public static <T> List<T> retainAll(List<T> masterList, List<T> branchList) {
        boolean result = masterList.retainAll(branchList);
        return masterList;
    }

    /**
     * 数组转换成列表
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(T... array) {
        if (array == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (T t : array) {
            list.add(t);
        }
        return list;
    }

    /**
     * 字符串转换成List<String>对象
     *
     * @param str   字符串
     * @param split 分隔符
     * @return
     */
    public static List<String> strTolist(String str, String split) {
        ArrayList<String> list = new ArrayList<>();
        if (TextUtils.isEmpty(str)) {
            return list;
        }
        String[] e = str.split(split);
        for (String i : e) {
            list.add(i);
        }
        return list;
    }

    /**
     * 列表转换成带分隔符的字符串
     *
     * @param list  字符串列表
     * @param split 分隔符
     * @return
     */
    public static String listToStr(List<String> list, String split) {
        StringBuilder sb = new StringBuilder();
        for (Object value : list) {
            sb.append(value.toString() + split);
        }
        String t = sb.toString();
        if (t.endsWith(split)) {
            int end = t.length() - split.length();
            t = t.substring(0, end);
        }
        return t;
    }

    /**
     * 列表排序
     *
     * @param list
     * @param <T>
     */
    public static <T> void sort(List<T> list) {
        Collections.sort(list, null);
    }

    /**
     * 列表排序(自定义规则)
     *
     * @param list
     * @param c
     * @param <T>
     */
    public static <T> void sort(List<T> list, Comparator<? super T> c) {
        Collections.sort(list, c);
    }

    /**
     * 深度拷贝
     *
     * @param src
     * @param <T>
     * @return
     */
    public static <T> List<T> deepCopyList(List<T> src) {
        List<T> dest = null;
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            dest = (List<T>) in.readObject();
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {

        }
        return dest;
    }
}
