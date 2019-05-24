/*
 * Copyright (c) 2015. SJY.JIANGSU Corporation. All rights reserved
 */

package com.ps.lc.utils.file;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by substring on 2016/8/3.
 * Email：zhangxuan@feitaikeji.com
 */
public class IOUtil {

    public static void writeFileFromInputStream(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead;
            int size = 1024 * 2;
            byte[] buffer = new byte[size];
            while ((bytesRead = ins.read(buffer, 0, size)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }

    public static byte[] readByteArrayFromInputStream(InputStream ins) {
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
            Log.getStackTraceString(e);
        } finally {
            safeClose(bos);
            safeClose(ins);
        }
        return res;
    }

    public static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                Log.getStackTraceString(e);
            }
        }
    }
}
