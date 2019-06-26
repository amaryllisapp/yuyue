package com.ps.lc.net.cache;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ps.lc.net.Utils;
import com.ps.lc.net.encryption.ICacheEncryption;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;


/**
 * Created by zhangwulin on 2016/11/9.
 * Eamil zhangwulin@feitaikeji.com
 */

public class DiskLruCacheHelper {
    private DiskLruCache mDiskLruCache;
    private static final int DEFAULT_VALUE_COUNT = 1;
    private static final int VERSION = 201901;
    private File mDir;//保存的目录
    private long mMaxSize;
    private Gson mGson = new Gson();
    private ICacheEncryption mCacheEncyption;

    public DiskLruCacheHelper(File dir, long maxSize) {
        this.mDir = dir;
        this.mMaxSize = maxSize;
    }

    /**
     * 使用顺序open put get close
     *
     * @throws IOException
     */
    public void open() throws IOException {
        close();
        mDiskLruCache = DiskLruCache.open(mDir, VERSION, DEFAULT_VALUE_COUNT, mMaxSize);
    }

    /**
     * 写缓存
     *
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        DiskLruCache.Editor edit = null;
        BufferedWriter bw = null;
        OutputStream os = null;
        try {
            edit = editor(key);
            if (edit == null) {
                return;
            }
            os = edit.newOutputStream(0);
            bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(null != mCacheEncyption ? mCacheEncyption.encrypt(value) : value);
            bw.flush();
            edit.commit();
        } catch (IOException e) {
            Log.getStackTraceString(e);
            try {
                //s
                edit.abort();
            } catch (IOException e1) {
                Log.getStackTraceString(e1);
            }
        } finally {
            Utils.closeQuietly(os);
            Utils.closeQuietly(bw);
        }
    }

    /**
     * 获取editor 对key进行md5
     *
     * @param key
     * @return
     */
    public DiskLruCache.Editor editor(String key) {
        try {
            key = Utils.getMD5(key);
            return mDiskLruCache.edit(key);
        } catch (IOException e) {
            Log.getStackTraceString(e);
        }
        return null;
    }


    /**
     * 获取流，对key进行md5
     * 缓存超时删除并返回null
     *
     * @param key
     * @param timeout      单位s ,-1为不用超时
     * @param expireDelete 过期就删除
     * @return
     */
    public Cache<String> get(String key, int timeout, boolean expireDelete) {
        InputStream is = null;
        try {
            String md5key = Utils.getMD5(key);
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(md5key);
            if (snapshot == null) {
                return null;
            }
            is = snapshot.getInputStream(0);

            if (null == is) {
                return null;
            }
            boolean isExpire = -1 != timeout && System.currentTimeMillis() >= snapshot.getSaveTime() + timeout * 1000;
            if (expireDelete && isExpire) {
                mDiskLruCache.remove(md5key);
                return null;
            }

            String str = Utils.readFully(new InputStreamReader(is, Utils.UTF_8));
            if (null != mCacheEncyption) {
                str = mCacheEncyption.decrypt(str);
            }
            return new Cache<>(str, isExpire);

        } catch (IOException e) {
            Log.getStackTraceString(e);
            return null;
        } finally {
            Utils.closeQuietly(is);
        }
    }


    /**
     * 保存Pojo,以json格式保存的
     *
     * @param key
     * @param t
     * @param <T>
     */
    public <T> void put(String key, T t) {
        put(key, mGson.toJson(t));
    }

    /**
     * 获取对象
     *
     * @param key
     * @param timeout      单位s ,-1为不用超时
     * @param classOfT
     * @param expireDelete 过期就删除
     * @return
     */
    public <T> Cache<T> get(String key, int timeout, Class<T> classOfT, boolean expireDelete) {
        Cache<String> val = get(key, timeout, expireDelete);
        try {
            if (val != null) {
                T t = mGson.fromJson(val.getData(), classOfT);
                if (null != t) {
                    return new Cache<>(t, val.isExpire());
                }
            }
        } catch (JsonSyntaxException e) {
            Log.getStackTraceString(e);
        }
        return null;
    }

    /**
     * 获取对象
     *
     * @param key
     * @param timeout      单位s ,-1为不用超时
     * @param typeOfT
     * @param expireDelete 过期就删除
     * @return
     */
    public <T> Cache<T> get(String key, int timeout, Type typeOfT, boolean expireDelete) {
        Cache<String> val = get(key, timeout, expireDelete);
        try {
            if (val != null) {
                T t = mGson.fromJson(val.getData(), typeOfT);
                if (null != t) {
                    return new Cache<>(t, val.isExpire());
                }
            }
        } catch (JsonSyntaxException e) {
            Log.getStackTraceString(e);
        }
        return null;
    }


    /**
     * 删除指定key的缓存
     *
     * @param key
     * @return
     */
    public boolean remove(String key) {
        if (null != mDiskLruCache) {
            try {
                return mDiskLruCache.remove(Utils.getMD5(key));
            } catch (IOException e) {
                Log.getStackTraceString(e);
            }
        }
        return false;
    }

    public void close() throws IOException {
        if (null != mDiskLruCache) {
            mDiskLruCache.close();
            mDiskLruCache = null;
        }
    }

    /**
     * 删除所有缓存
     */
    public void delete() throws IOException {
        if (null != mDiskLruCache) {
            close();
            mDiskLruCache.delete();
        }
    }

    public void flush() throws IOException {
        if (null != mDiskLruCache) {
            mDiskLruCache.flush();
        }
    }

    public boolean isClosed() {
        return null == mDiskLruCache || mDiskLruCache.isClosed();
    }

    public long size() {
        return null != mDiskLruCache ? mDiskLruCache.size() : 0;
    }

    public ICacheEncryption getCacheEncyption() {
        return mCacheEncyption;
    }

    public void setCacheEncyption(ICacheEncryption mCacheEncyption) {
        this.mCacheEncyption = mCacheEncyption;
    }
}