/*
 *  * Copyright (C) 2015-2015 SJY.JIANGSU Corporation. All rights reserved
 */

package com.ps.lc.utils.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.ps.lc.utils.file.IOUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static android.content.Context.MODE_PRIVATE;

/**
 * 类名：SharedManager
 * 描述：SharedPreferences 工具类,直接在Application中运行
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/5/24 17:36
 */
public class SharedManager {

    private static final String PREFERENCE_NAME = "setting_flags";
    private static SharedPreferences sSharePreference;

    public static void init(Context context) {
        sSharePreference = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
    }

    public static void put(String key, String value) {
        sSharePreference.edit().putString(key, value).commit();
    }

    public static void put(String key, int value) {
        sSharePreference.edit().putInt(key, value).commit();
    }

    public static void put(String key, boolean value) {
        sSharePreference.edit().putBoolean(key, value).commit();
    }

    public static void put(String key, long value) {
        sSharePreference.edit().putLong(key, value).commit();
    }

    public static boolean contains(String key) {
        return sSharePreference.contains(key);
    }

    public static int get(String key) {
        return getInt(key, 0);
    }

    public static int getInt(String key, int defValue) {
        return sSharePreference.getInt(key, defValue);
    }

    public static long getLong(String key) {
        return getLong(key, 0);
    }

    public static long getLong(String key, long defValue) {
        return sSharePreference.getLong(key, defValue);
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return sSharePreference.getBoolean(key, defValue);
    }

    public static String getString(String key) {
        return getString(key, null);
    }

    public static String getString(String key, String defValue) {
        return sSharePreference.getString(key, defValue);
    }

    /**
     * 删除某一个KEY
     *
     * @param key
     */
    public static void remove(String key) {
        sSharePreference.edit().remove(key).commit();
    }

    /**
     * 清除所有的数据
     */
    public static void clear() {
        sSharePreference.edit().clear().commit();
    }

    /**
     * 保存数据对象
     *
     * @param context
     * @param key
     * @param object
     */
    public static void saveData(Context context, String key, Object object) {
        saveData(context, PREFERENCE_NAME, key, object);
    }

    /**
     * 删除数据对象
     *
     * @param context
     * @param name
     * @param key
     */
    public static void removeData(Context context, String name, String key) {
        SharedPreferences sharePre = context.getSharedPreferences(name, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePre.edit();
        editor.remove(key).commit();
    }

    /**
     * 写入数据对象
     *
     * @param context
     * @param name
     * @param key
     * @param object
     */
    public static void saveData(Context context, String name, String key, Object object) {
        SharedPreferences sharePre = context.getSharedPreferences(name, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePre.edit();
        //创建字节输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            // 创建对象输出流，并封装字节流
            oos = new ObjectOutputStream(outputStream);
            // 将对象写入字节流
            oos.writeObject(object);
            String objectString = new String(Base64.encode(outputStream.toByteArray(), Base64.DEFAULT));
            editor.putString(key, objectString);
            editor.apply();
        } catch (IOException e) {
            Log.getStackTraceString(e);
        } finally {
            IOUtil.safeClose(oos);
            IOUtil.safeClose(outputStream);
        }
    }

    /**
     * 获取数据对象
     *
     * @param context
     * @param key
     * @return
     */
    public static Object getData(Context context, String key) {
        return getData(context, PREFERENCE_NAME, key);
    }

    /**
     * 获取数据对象
     *
     * @param context
     * @param name
     * @param key
     * @return
     */
    public static Object getData(Context context, String name, String key) {
        Object object = null;
        SharedPreferences preferences = context.getSharedPreferences(name, MODE_PRIVATE);
        String objectString = preferences.getString(key, "");
        byte[] bytes = Base64.decode(objectString.getBytes(), Base64.DEFAULT);
        //封装到字节流
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream bis = null;
        try {
            //再次封装
            bis = new ObjectInputStream(inputStream);
            try {
                //读取对象
                object = bis.readObject();
            } catch (ClassNotFoundException e) {
                Log.getStackTraceString(e);
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } finally {
            IOUtil.safeClose(bis);
            IOUtil.safeClose(inputStream);
        }
        return object;
    }

    /**
     * 清除数据对象
     *
     * @param context
     * @param name
     */
    public static void clearData(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(name, MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear();
        edit.commit();
    }
}
