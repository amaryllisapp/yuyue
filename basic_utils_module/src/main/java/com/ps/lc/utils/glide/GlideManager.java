package com.ps.lc.utils.glide;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.ps.lc.utils.comm.DisplayUtil;
import com.ps.lc.utils.glide.transform.GlideCircleTransform;
import com.ps.lc.utils.glide.transform.GlideRoundTransform;

import java.io.File;

/**
 * Glide 工具类
 *
 * @author 36077 - liucheng@xhg.com
 * @date 2018/12/13 15:54
 */
public class GlideManager {
    /**
     * 显示图片
     *
     * @param context
     * @param url
     * @param iv
     */
    public static void showImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).into(iv);
    }

    /**
     * 显示本地图片
     *
     * @param context
     * @param url
     * @param iv
     */
    public static void showLocalImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).into(iv);
    }

    /**
     * 显示圆形图片（远程）
     *
     * @param context
     * @param url
     * @param iv
     * @param placeholder
     * @param error
     */
    public static void showCircleImage(Context context, String url, ImageView iv, int placeholder, int error) {
        Glide.with(context).load(url).apply(getCircleOption(placeholder, error)).into(iv);
    }

    /**
     * 显示圆形图片（远程）
     *
     * @param context
     * @param url
     * @param iv
     * @param options
     */
    public static void showCircleImage(Context context, String url, ImageView iv, RequestOptions options) {
        Glide.with(context).load(url).apply(options).into(iv);
    }

    /**
     * 显示圆形图片（本地）
     *
     * @param context
     * @param file
     * @param iv
     * @param placeholder
     * @param error
     */
    public static void showCircleImage(Context context, File file, ImageView iv, int placeholder, int error) {
        Glide.with(context).load(file).apply(getCircleOption(placeholder, error)).into(iv);
    }

    /**
     * 显示圆形图片（本地）
     *
     * @param context
     * @param file
     * @param iv
     * @param options
     */
    public static void showCircleImage(Context context, File file, ImageView iv, RequestOptions options) {
        Glide.with(context).load(file).apply(options).into(iv);
    }

    /**
     * 显示方形圆角图片（远程图片显示）
     *
     * @param context
     * @param url
     * @param iv
     * @param placeholder
     * @param error
     */
    public static void showSquareRoundImage(Context context, String url, ImageView iv, int placeholder, int error, int with, int round) {
        Glide.with(context).load(url).apply(getSquareRoundOption(context, placeholder, error, with, round)).into(iv);
    }

    /**
     * 显示方形图片（远程图片显示）
     *
     * @param context
     * @param url
     * @param iv
     * @param options
     */
    public static void showSquareRoundImage(Context context, String url, ImageView iv, RequestOptions options) {
        Glide.with(context).load(url).apply(options).into(iv);
    }

    /**
     * 显示方形圆角图片(本地图片显示)
     *
     * @param context
     * @param file
     * @param iv
     * @param placeholder
     * @param error
     * @param with
     * @param round
     */
    public static void showSquareRoundImage(Context context, File file, ImageView iv, int placeholder, int error, int with, int round) {
        Glide.with(context).load(file).apply(getSquareRoundOption(context, placeholder, error, with, round)).into(iv);
    }

    /**
     * 显示方形圆角图片(本地图片显示)
     *
     * @param context
     * @param file
     * @param iv
     * @param options
     */
    public static void showSquareRoundImage(Context context, File file, ImageView iv, RequestOptions options) {
        Glide.with(context).load(file).apply(options).into(iv);
    }

    /**
     * 获取圆形图片选项
     *
     * @param placeholder
     * @param error
     * @return
     */
    public static RequestOptions getCircleOption(@DrawableRes int placeholder, @DrawableRes int error) {
        return new RequestOptions()
                .placeholder(placeholder)
                .error(error)
                .priority(Priority.HIGH)
                .transform(new GlideCircleTransform());
    }

    /**
     * 获取正方形带圆角图片选项
     *
     * @param context
     * @param placeholder
     * @param error
     * @param with
     * @param round       default:2
     * @return
     */
    public static RequestOptions getSquareRoundOption(Context context, @DrawableRes int placeholder, @DrawableRes int error, int with, int round) {
        return new RequestOptions()
                .placeholder(placeholder)
                .error(error)
                .priority(Priority.HIGH)
                .override(DisplayUtil.dip2px(context, with))
                .transform(new GlideRoundTransform(round));
    }

}
