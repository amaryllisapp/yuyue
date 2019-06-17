/*
 * Copyright (c) 2015. SJY.JIANGSU Corporation. All rights reserved
 */

package com.ps.lc.utils;

import android.util.Log;

import java.lang.reflect.Field;

public class ReflectionUtil {

    public final static int INVALID_VALUE = -1;

    public static int getIntFileValueFromClass(Class c, String filedName) {
        Field field;
        int fieldValue = 0;
        try {
            field = c.getDeclaredField(filedName);
            field.setAccessible(true);
            fieldValue = field.getInt(c);
        } catch (Exception ex) {
            Log.getStackTraceString(ex);
        }
        return fieldValue;
    }

    public static Object getFieldValue(Object object, String fieldName) {
        Class classType = object.getClass();
        Object fieldValue = null;
        try {
            Field field = classType.getDeclaredField(fieldName);
            field.setAccessible(true);
            fieldValue = field.get(object);

        } catch (NoSuchFieldException ex) {
            Log.getStackTraceString(ex);
        } catch (Exception ex) {
            Log.getStackTraceString(ex);
        }
        return fieldValue;
    }




}
