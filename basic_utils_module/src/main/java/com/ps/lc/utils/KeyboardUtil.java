package com.ps.lc.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import static android.content.ContentValues.TAG;

/**
 * Email:zhangwulin@feitaikeji.com
 *
 * @author zhangwulin
 * @date 2017/1/16
 */

public class KeyboardUtil {
    /**
     * 根视图显示高度变大超过200，可以看作软键盘隐藏
     */
    private static final int LAYOUT_CHANGE_HEIGHT = 200;


    /**
     * TODO 这个方法一直返回 有问题 ,任何情况下的返回值都为 true ,无法有效判断输入法是否弹出 -- By Zenfer
     * 键盘是否打开
     *
     * @param activity activity
     * @return true yes,false no
     */
    @Deprecated
    public static boolean isInputMethodShow(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm != null && imm.isActive();
    }

    /**
     * 打开键盘
     *
     * @param context 上下文
     */
    public static void showInputMethod(Context context, View inputView) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(inputView, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 打开键盘
     *
     * @param activity activity
     */
    public static void showInputMethod(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 打开键盘
     *
     * @param context context
     */
    public static void showInputMethod(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 针对给定的editText显示软键盘（editText会先获得焦点）. 可以和{@link #( View )}
     * 搭配使用，进行键盘的显示隐藏控制。
     */

    public static void showKeyboard(final EditText editText, int delay) {
        if (null == editText) {
            return;
        }
        if (!editText.requestFocus()) {
            Log.w(TAG, "showSoftInput() can not get focus");
            return;
        }
        if (delay > 0) {
            editText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager imm = (InputMethodManager) editText.getContext().getApplicationContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                }
            }, delay);
        } else {
            InputMethodManager imm = (InputMethodManager) editText.getContext().getApplicationContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * 关闭键盘
     *
     * @param activity activity
     */
    public static void hideInputMethod(Activity activity) {
        hideInputMethod(activity, activity.getWindow().peekDecorView());
    }

    /**
     * 关闭键盘
     *
     * @param activity activity
     * @param view     view
     */
    private static void hideInputMethod(Activity activity, View view) {
        if (view == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        IBinder token = view.getWindowToken();
        if (imm == null) {
            return;
        }
        boolean success = imm.hideSoftInputFromWindow(token, 0);
        if (!success) {
            Object serverObject = ReflectionUtil.getFieldValue(imm, "mServedView");
            if (serverObject instanceof View) {
                token = ((View) serverObject).getWindowToken();
            }
            success = imm.hideSoftInputFromWindow(token, 0);
        }
        Log.i(TAG, "hideInputMethod success = " + success);
    }

    /**
     * 处理键盘收起展开, 隐藏显示底部布局
     */
    public static void handleKeyBoardShowAndHide(final View rootView, final KeyBoardShowAndHideListener listener) {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect diffRect = new Rect();
                rootView.getWindowVisibleDisplayFrame(diffRect);
                int currentHeight = diffRect.bottom - diffRect.top;
                int rootViewHeight = rootView.getRootView().getHeight();
                int heightDiff = rootViewHeight - currentHeight;
                if (listener == null) {
                    return;
                }
                //根视图显示高度变大超过跟布局高度的1/6，可以看作软键盘隐藏
                int layoutChangeHeight = rootViewHeight / 6;
                if (heightDiff > layoutChangeHeight) {
//                    Log.i("onGlobalLayout", "  onKeyBoardShow:  "
//                            + "  heightDiff:  " + String.valueOf(heightDiff)
//                            + "  currentHeight:  " + String.valueOf(currentHeight)
//                            + "  rootViewHeight:  " + String.valueOf(rootViewHeight)
//                            + "  layoutChangeHeight:  " + String.valueOf(layoutChangeHeight));
                    listener.onKeyBoardShow();
                } else {
                    listener.onKeyBoardHide();
//                    Log.i("onGlobalLayout", "  onKeyBoardHide:  "
//                            + "  heightDiff:  " + String.valueOf(heightDiff)
//                            + "  currentHeight:  " + String.valueOf(currentHeight)
//                            + "  rootViewHeight:  " + String.valueOf(rootViewHeight)
//                            + "  layoutChangeHeight:  " + String.valueOf(layoutChangeHeight));
                }
            }
        });
    }

    /**
     * 键盘张开收起监听
     */
    public interface KeyBoardShowAndHideListener {
        /**
         * 键盘隐藏
         */
        void onKeyBoardHide();

        /**
         * 键盘打开
         */
        void onKeyBoardShow();
    }

}
