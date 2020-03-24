package com.ps.lc.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Gson工具类
 *
 * @author caoruijia
 * @date 2018/3/15
 */
public class GsonUtil {

    /**
     * 普通的Gson对象
     */
    private static Gson mGsonByNormal;
    /**
     * 自定义配置Gson对象
     */
    private static Gson mGsonByBuilder;

    /**
     * 转json字符串
     *
     * @param obj 对象
     * @return json字符串
     */
    public static String toJsonString(Object obj) {
        return toJsonString(obj, false);
    }

    /**
     * 转json字符串
     *
     * @param obj          对象
     * @param useAnotation 是否使用自定义配置gson对象
     * @return json字符串
     */
    public static String toJsonString(Object obj, boolean useAnotation) {
        initGson(useAnotation);
        return getGson(useAnotation).toJson(obj);
    }

    /**
     * json转实体类
     * <p>
     * 实体类转换正常使用此方法
     *
     * @param json json字符串
     * @param clz  Class
     * @param <T>  对应实体类
     * @return 对应实体类对象
     */
    public static <T> T toObject(String json, Class<T> clz) {
        return toObject(json, clz, false);
    }

    /**
     * json转实体类
     * <p>
     * 实体类转换正常使用此方法
     *
     * @param json         json字符串
     * @param clz          Class
     * @param useAnotation 是否使用自定义配置gson对象
     * @param <T>          对应实体类
     * @return 对应实体类对象
     */
    public static <T> T toObject(String json, Class<T> clz, boolean useAnotation) {
        initGson(useAnotation);
        try {
            return getGson(useAnotation).fromJson(json, clz);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * json转list，需要知道 List中具体的实体类对象
     * <p>
     * List的转换默认使用此方法
     *
     * @param json json字符串
     * @param clz  Class
     * @param <T>  对应实体类
     * @return 对应实体类对象的数组
     */
    public static <T> List<T> toList(String json, Class clz) {
        return toList(json, clz, false);
    }

    /**
     * json转list，需要知道 List中具体的实体类对象
     * <p>
     * List的转换默认使用此方法
     *
     * @param json         json字符串
     * @param clz          Class
     * @param useAnotation 是否使用自定义配置gson对象
     * @param <T>          对应实体类
     * @return 对应实体类对象的数组
     */
    public static <T> List<T> toList(String json, Class clz, boolean useAnotation) {
        initGson(useAnotation);
        try {
            Type type = new ParameterizedTypeImpl(clz);
            return getGson(useAnotation).fromJson(json, type);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * json转成 map，默认key为 String
     *
     * @param json json字符串
     * @param <T>  对应实体类
     * @return 对应实体类对象的数组
     */
    public static <T> Map<String, T> toMap(String json) {
        return toMap(json, false);
    }

    /**
     * json转成 map，默认key为 String
     *
     * @param json         json字符串
     * @param useAnotation 是否使用自定义配置gson对象
     * @param <T>          对应实体类
     * @return 对应实体类对象的数组
     */
    public static <T> Map<String, T> toMap(String json, boolean useAnotation) {
        initGson(useAnotation);
        try {
            return getGson(useAnotation).fromJson(json, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * json转对应的实体类，这是一个通用性的方法，下面是这个方法的使用方式 :
     * <p>
     * 1.List的转换
     * List<MapSiteNet>  data = GsonUtil.toObject(result.toString(), new TypeToken<List<MapSiteNet>>(){}.getType());
     * <p>
     * 2.Bean的转换
     * MapSiteNet data = GsonUtil.toObject(result.toString(), new TypeToken<MapSiteNet>(){}.getType());
     */
    public static <T> T toCommonObject(String json, Type type) {
        return toCommonObject(json, type, false);
    }

    /**
     * * json转对应的实体类，这是一个通用性的方法，下面是这个方法的使用方式 :
     * <p>
     * 1.List的转换
     * List<MapSiteNet>  data = GsonUtil.toObject(result.toString(), new TypeToken<List<MapSiteNet>>(){}.getType());
     * <p>
     * 2.Bean的转换
     * MapSiteNet data = GsonUtil.toObject(result.toString(), new TypeToken<MapSiteNet>(){}.getType());
     * <p>
     * 3.Map的转换
     * Map<Point, String> retMap2 = gson.fromJson(jsonStr2, new TypeToken<Map<Point, String>>() {}.getType());
     *
     * @param json         json字符串
     * @param type
     * @param useAnotation 是否使用自定义配置gson对象
     * @param <T>          对应实体类
     * @return
     */
    public static <T> T toCommonObject(String json, Type type, boolean useAnotation) {
        initGson(useAnotation);
        try {
            return getGson(useAnotation).fromJson(json, type);
        } catch (Exception e) {
            return null;
        }
    }

    private static class ParameterizedTypeImpl implements ParameterizedType {
        Class clazz;

        public ParameterizedTypeImpl(Class clz) {
            clazz = clz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }

    /**
     * 初始化 Gson 对象
     *
     * @param userAnotation 是否使用自定义配置gson对象
     */
    private static void initGson(boolean userAnotation) {
        if (userAnotation) {
            if (mGsonByBuilder == null) {
                //对这种初始化方式不了解的请自行百度
                mGsonByBuilder = new GsonBuilder()
                        // json宽松
                        .setLenient()
                        //支持Map的key为复杂对象的形式
                        .enableComplexMapKeySerialization()
                        //智能null,支持输出值为null的属性
                        .serializeNulls()
                        //格式化输出（序列化）
                        .setPrettyPrinting()
                        //序列化日期格式化输出
//                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        //不序列化与反序列化没有@Expose标注的字段
                        .excludeFieldsWithoutExposeAnnotation()
                        //默认是Gson把HTML转义的
//                        .disableHtmlEscaping()
                        .create();
            }
        } else {
            if (mGsonByNormal == null) {
                mGsonByNormal = new Gson();
            }
        }
    }

    /**
     * 获取 Gson 对象
     *
     * @param userAnotation 是否使用自定义配置gson对象
     * @return Gson 对象
     */
    private static Gson getGson(boolean userAnotation) {
        return userAnotation ? mGsonByBuilder : mGsonByNormal;
    }

}
