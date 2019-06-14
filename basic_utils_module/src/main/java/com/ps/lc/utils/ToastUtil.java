package com.ps.lc.utils;

import android.content.Context;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;


/**
 * Created by substring on 2016/8/1.
 * Email：zhangxuan@feitaikeji.com
 */
public class ToastUtil {

    private static Toast sToast;
    private static TextView sMessage;//// TODO: 2016/12/28 产生内存泄露

    public static void showToast(Context context, int resId) {
        String message = context.getResources().getString(resId);
        showToast(context, message);
    }

    public static void showToast(final Context context, final String text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示toast
     *
     * @param isLong true:  Toast.LENGTH_SHORT;  false: Toast.LENGTH_LONG
     */
    public static void showToast(final Context context, final String text, boolean isLong) {
        showToast(context, text, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
    }

    public static void showToast(final Context context, final String text, final int duration) {
        WeakReference<Context> weakReference = new WeakReference<>(context);
        final Context weakContext = weakReference.get();
        if (weakContext == null) {
            return;
        }
        ThreadManager.post(ThreadManager.THREAD_UI, new Runnable() {
            @Override
            public void run() {
                if (sToast == null) {
                    sToast = new Toast(context.getApplicationContext());
                    View view = View.inflate(weakContext, R.layout.common_toast_layout, null);
                    view.getBackground().setAlpha(150);
                    sMessage = (TextView) view.findViewById(R.id.common_toast_msg);
                    sToast.setGravity(Gravity.CENTER, 0, ResourceUtil.getDimen(R.dimen.sx30));
                    sToast.setView(view);
                }
                sToast.setDuration(duration);
                sMessage.setText(text);
                sToast.show();
            }
        });
    }

    public static void showToast(final Context context, final Spanned text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    public static void showToast(final Context context, final Spanned text, final int duration) {
        WeakReference<Context> weakReference = new WeakReference<>(context);
        final Context weakContext = weakReference.get();
        if (weakContext == null) {
            return;
        }
        ThreadManager.post(ThreadManager.THREAD_UI, new Runnable() {
            @Override
            public void run() {
                if (sToast == null) {
                    sToast = new Toast(context.getApplicationContext());
                    View view = View.inflate(weakContext, R.layout.common_toast_layout, null);
                    view.getBackground().setAlpha(150);
                    sMessage = (TextView) view.findViewById(R.id.common_toast_msg);
                    sToast.setGravity(Gravity.CENTER, 0, ResourceUtil.getDimen(R.dimen.sx30));
                    sToast.setView(view);
                }
                sToast.setDuration(duration);
                sMessage.setText(text);
                sToast.show();
            }
        });
    }

    /**
     * should use it before activity finished;
     */
    public static void closeToast() {
        if (sToast == null) {
            return;
        }
        sToast.cancel();
        sToast = null;
        sMessage = null;
    }
}
