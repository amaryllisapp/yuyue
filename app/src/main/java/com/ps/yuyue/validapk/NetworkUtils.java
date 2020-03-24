package com.ps.yuyue.validapk;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * <br> ClassName:   NetworkUtils
 * <br> Description: 网络工具
 * <br>
 * <br> Author:      fangbingran
 * <br> Date:        2018/6/12 21:47
 */
public class NetworkUtils {
    public static final Charset US_ASCII = Charset.forName("US-ASCII");
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    private NetworkUtils() {
    }


    public static String readFully(Reader reader) throws IOException {
        try {
            StringWriter writer = new StringWriter();
            char[] buffer = new char[1024];
            int count;
            while ((count = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, count);
            }
            return writer.toString();
        } finally {
            closeQuietly(reader);
        }
    }

    public static void deleteContents(File dir) throws IOException {
        File[] files = dir.listFiles();
        if (files == null) {
            throw new IOException("not a readable directory: " + dir);
        }
        for (File file : files) {
            if (file.isDirectory()) {
                deleteContents(file);
            }
            if (!file.delete()) {
                throw new IOException("failed to delete file: " + file);
            }
        }
    }


    public static void closeQuietly(Closeable... closeable) {
        if (null == closeable || closeable.length <= 0) {
            return;
        }
        for (Closeable cb : closeable) {
            try {
                if (null == cb) {
                    continue;
                }
                cb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 是否可以连网
     *
     * @param appContext app
     * @param cache      是否用缓存
     * @return
     */
    public static boolean isNetwork(Context appContext, boolean cache) {
        if (null == appContext) {//这个主要用于test
            return true;
        }
        if (!cache) {
            return isNetworkAvailable(appContext);
        } else {
            return true;
        }
    }

    /**
     * 描述：判断网络是否有效.
     *
     * @return true, if is network available
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    return info.getState() == NetworkInfo.State.CONNECTED;
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
            return false;
        }
        return false;
    }


    /**
     * 获取cache dir
     */
    public static File getCacheDir(Context context) {
        File appCacheDir = getExternalCacheDir(context);
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
            if (appCacheDir != null && !appCacheDir.exists() && !appCacheDir.mkdirs()) {
                appCacheDir = null;
            }
            if (appCacheDir == null) {
                String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
                appCacheDir = new File(cacheDirPath);
                if (!appCacheDir.exists()) {
                    appCacheDir.mkdirs();
                }
            }
        }
        return appCacheDir;
    }

    private static File getExternalCacheDir(Context context) {
        File appCacheDir = context.getExternalCacheDir();
        if (appCacheDir != null && !appCacheDir.exists() && !appCacheDir.mkdirs()) {
            appCacheDir = null;
        }
        if (appCacheDir == null) {
            File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
            appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
            if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                appCacheDir = null;
            }
        }
        return appCacheDir;
    }

    /**
     * 获取type
     */
    public static Type getType(Object o, int index) {
        Class oClass = o.getClass();
        Type type = oClass.getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            type = oClass.getSuperclass().getGenericSuperclass();
        }
        return ((ParameterizedType) type).getActualTypeArguments()[index];
    }

    /**
     * 转md5
     *
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] b = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.getStackTraceString(e);
            return null;
        }
    }
    private static byte[] readByteArrayFromInputStream(InputStream ins) {
        byte[] res = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            int len;
            int size = 1024 * 2;
            byte[] buf = new byte[size];
            while ((len = ins.read(buf, 0, size)) != -1) {
                bos.write(buf, 0, len);
            }
            res = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(bos, ins);
        }
        return res;
    }
    /**
     * Description:转换信息
     * Author:     fangbingran
     * Date:        2018/5/7 22:02
     *
     * @param input     byte
     * @param algorithm 所需要转换的"SHA-256","md5"
     * @return String
     */
    public static String getByteToAlgorithm(byte[] input, String algorithm) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(input);
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * Description:转换信息
     * Author:     fangbingran
     * Date:        2018/5/7 22:02
     *
     * @param file      File
     * @param algorithm 所需要转换的"SHA-256","md5"
     * @return String
     */
    public static String getFileToAlgorithm(File file, String algorithm) {
        if (file == null) {
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            return getInputStreamToAlgorithm(fis, algorithm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Description:转换信息
     * Author:     fangbingran
     * Date:        2018/5/7 22:02
     *
     * @param ins       InputStream
     * @param algorithm 所需要转换的"SHA-256","md5"
     * @return String
     */
    public static String getInputStreamToAlgorithm(InputStream ins, String algorithm) {
        return getByteToAlgorithm(readByteArrayFromInputStream(ins), algorithm);
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return String
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuilder.append("0");
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }


}
