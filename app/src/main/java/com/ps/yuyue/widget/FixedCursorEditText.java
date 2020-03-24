package com.ps.yuyue.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * 类名：com.ps.yuyue.widget
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/10/23 18:39
 */
public class FixedCursorEditText extends AppCompatEditText {

    private CharSequence mHint;

    private Paint mHintPaint;
    private int mCurHintTextColor;

    public FixedCursorEditText(Context context) {
        this(context, null);
    }

    public FixedCursorEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.editTextStyle);
    }

    public FixedCursorEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mHint = getHint();
        setHint("");
        mHintPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mHintPaint.setTextSize(getTextSize());
        mHintPaint.setTextAlign(Paint.Align.RIGHT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (TextUtils.isEmpty(mHint) || !TextUtils.isEmpty(getText())) {
            return;
        }
        canvas.save();
        ColorStateList hintTextColors = getHintTextColors();
        if (hintTextColors != null) {
            int color = hintTextColors.getColorForState(getDrawableState(), 0);
            if (color != mCurHintTextColor) {
                mCurHintTextColor = color;
                mHintPaint.setColor(color);
            }
        }

        Paint.FontMetricsInt fontMetrics = mHintPaint.getFontMetricsInt();
        int baseline = (getHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(mHint, 0, mHint.length(),
                getWidth() - getPaddingRight() + getScrollX(),
                baseline, mHintPaint);
        canvas.restore();
    }
}
