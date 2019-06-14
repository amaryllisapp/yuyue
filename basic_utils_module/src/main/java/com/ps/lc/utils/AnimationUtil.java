package com.ps.lc.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

public class AnimationUtil {

    /**
     * 向左平移
     *
     * @return
     */
    public static TranslateAnimation getTranslateLeftVisible() {
        return getTranslateLeftVisible(500);
    }

    /**
     * 向左平移
     *
     * @return
     */
    public static TranslateAnimation getTranslateLeftVisible(int duration) {
        TranslateAnimation showAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        showAction.setDuration(duration);
        return showAction;
    }

    /**
     * 向左平移
     *
     * @return
     */
    public static TranslateAnimation getTranslateLeftGone(int duration) {
        TranslateAnimation showAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        showAction.setDuration(duration);
        return showAction;
    }

    /**
     * 向右平移
     *
     * @return
     */
    public static TranslateAnimation getTranslateRightVisible() {
        TranslateAnimation showAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        showAction.setDuration(500);
        return showAction;
    }

    public static AlphaAnimation getAlphaAnimationVisible() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(300);
        return alphaAnimation;
    }

    public static void getAlphaAnimatorSet(ValueAnimator.AnimatorUpdateListener listener) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 0f);
        valueAnimator.setDuration(800);
        valueAnimator.addUpdateListener(listener);
        valueAnimator.start();
    }

    public static void getScaleAnimatorSet(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.9f, 1f, 1.1f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.9f, 1f, 1.1f, 1f);
        animatorSet.setDuration(200);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.start();
    }

    public static void getScaleAnimatorSetShow(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0.3f, 0.5f, 0.7f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 0.3f, 0.5f, 0.7f, 1f);
        animatorSet.setDuration(200);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.start();
    }

    public static void getScaleAnimatorSetShowWithBounce(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0.1f, 0.3f, 0.5f, 0.7f, 1f, 1.2f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 0.1f, 0.3f, 0.5f, 0.7f, 1f, 1.2f, 1f);
        animatorSet.setDuration(200);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.start();
    }

    public static void getScaleAnimatorSetShow(View view, ValueAnimator.AnimatorUpdateListener listener) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0.6f, 0.7f, 0.8f, 0.9f, 1f);
        scaleXAnimator.addUpdateListener(listener);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 0.6f, 0.7f, 0.8f, 0.9f, 1f);
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.start();
    }


    public static void getScaleAnimatorSetHide(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.7f, 0.5f, 0f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.7f, 0.5f, 0f);
        animatorSet.setDuration(200);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.start();
    }

    public static RotateAnimation getRotateAnimation(float startAngle, float endAngle) {
        RotateAnimation rotateAnimation = new RotateAnimation(startAngle, endAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(200);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);
        return rotateAnimation;
    }
}
