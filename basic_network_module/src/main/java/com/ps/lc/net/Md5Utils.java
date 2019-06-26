/*
 * Copyright (c) 2015. SJY.JIANGSU Corporation. All rights reserved
 */
package com.ps.lc.net;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {

    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static char[] encodeHex(byte[] data) {
        return encodeHex(data, false);
    }

    private static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    private static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        int i = 0;
        for (int j = 0; i < l; i++) {
            out[(j++)] = toDigits[((0xF0 & data[i]) >>> 4)];
            out[(j++)] = toDigits[(0xF & data[i])];
        }
        return out;
    }

    private static String encodeHexString(byte[] data) {
        return new String(encodeHex(data));
    }

    public static String getMD5(String str) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
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
            return null;
        }
    }

    public static byte[] getMD5ByteArray(byte[] bArr) {
        try {
            if (bArr == null) {
                return null;
            }
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.reset();
            instance.update(bArr);
            return instance.digest();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getMD5(byte[] bytes) {
        byte[] result = getMD5ByteArray(bytes);
        if (result == null) {
            return null;
        }
        return encodeHexString(result);
    }
}