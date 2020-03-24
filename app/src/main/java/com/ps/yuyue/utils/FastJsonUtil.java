package com.ps.yuyue.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.util.TypeUtils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

/**
 * <br> ClassName:   FastJsonUtil
 * <br> Description: fastjson转换工具
 * <br>
 * <br> Author:      fangbingran
 * <br> Date:       2018/6/23.
 */
public class FastJsonUtil {

    public static <T> T toJSONInputStream(InputStream inputStream, Type type) {
        return toJSONObject(ConvertStream2Json(inputStream), type);
    }

    public static <T> List<T> toJSONListInputStream(InputStream inputStream, Class<T> clazz) {
        return toJSONOList(ConvertStream2Json(inputStream), clazz);
    }

    public static <T> T toJSONObject(Object object) {
        String jsonString = toJSONString(object);
        T t = null;
        try {
            t = JSON.parseObject(jsonString, new TypeReference<T>() {
            });
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        return t;
    }

    public static <T> T toJSONObject(Object object, Type type) {
        String jsonString = toJSONString(object);
        return toJSONObject(jsonString, type);
    }

    public static <T> T toJSONObject(String jsonString, Type type) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, type);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        return t;
    }

    public static <T> List<T> toJSONOList(String jsonString, Class<T> clazz) {
        List<T> list = null;
        try {
            list = JSON.parseArray(jsonString, clazz);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        return list;
    }

    public static List<Object> toJSONOList(String jsonString, Type[] types) {
        List<Object> list = null;
        try {
            list = JSON.parseArray(jsonString, types);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        return list;
    }

    public static List<Object> toJSONOList(String jsonString, Type types) {
        return toJSONOList(jsonString, new Type[]{types});
    }

    public static boolean isList(Type type) {
        if (TypeUtils.getClass(type) == List.class) {
            return true;
        }
        return false;
    }

    public static String toJSONString(Object object) {
        String jsonString = "";
        try {
            jsonString = JSON.toJSONString(object);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        return jsonString;
    }

    private static String ConvertStream2Json(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        String jsonStr = "";
        // ByteArrayOutputStream相当于内存输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        // 将输入流转移到内存输出流中
        try {
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, len);
            }
            // 将内存流转换为字符串
            jsonStr = new String(out.toByteArray());
        } catch (IOException e) {
            Log.getStackTraceString(e);
        } finally {
            closeIO(inputStream, out);
        }
        return jsonStr;
    }


    /**
     * <br> Description: 关闭流
     * <br> Author:     fangbingran
     * <br> Date:        2018/6/25 22:14
     *
     * @param closeables
     */
    private static void closeIO(Closeable... closeables) {
        if (null == closeables || closeables.length <= 0) {
            return;
        }
        for (Closeable cb : closeables) {
            try {
                if (null == cb) {
                    continue;
                }
                cb.close();
            } catch (IOException e) {
                Log.getStackTraceString(e);
            }
        }
    }
}
