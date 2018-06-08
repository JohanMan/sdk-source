package com.johan.base.recycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by johan on 2018/6/7.
 */

public abstract class RecyclerDecoration extends RecyclerView.ItemDecoration {

    /** 系统分割线的样式 */
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    private int lineWidth;
    private int lineColor;
    private Drawable lineDrawable;

    private Paint paint;

    /**
     * 直接提供 线宽 线颜色
     * @param lineWidth
     * @param lineColor
     */
    public RecyclerDecoration(int lineWidth, int lineColor) {
        this.lineWidth = lineWidth;
        this.lineColor = lineColor;
        initPaint();
    }

    /**
     * 提供 线Drawable
     * @param lineDrawable
     */
    public RecyclerDecoration(Drawable lineDrawable) {
        this.lineDrawable = lineDrawable;
        this.lineWidth = lineDrawable.getIntrinsicHeight();
    }

    /**
     * 提供 自定义Drawable的Id
     * @param context
     * @param drawableId
     */
    public RecyclerDecoration(Context context, int drawableId) {
        this.lineDrawable = ContextCompat.getDrawable(context, drawableId);
        this.lineWidth = lineDrawable.getIntrinsicHeight();
    }

    /**
     * 指定系统默认分割线样式
     * @param context
     */
    public RecyclerDecoration(Context context) {
        TypedArray a = context.obtainStyledAttributes(ATTRS);
        this.lineDrawable = a.getDrawable(0);
        a.recycle();
        this.lineWidth = lineDrawable.getIntrinsicHeight();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(lineColor);
    }

    /**
     * 画水平线
     * @param child
     */
    protected void drawHorizontalLine(Canvas canvas, View child) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        int left = child.getLeft() - params.leftMargin;
        int right = child.getRight() + params.rightMargin + lineWidth;
        int top = child.getBottom() + params.bottomMargin;
        int bottom = child.getBottom() + params.bottomMargin + lineWidth;
        if (lineColor != 0) {
            canvas.drawRect(left, top, right, bottom, paint);
        } else if (lineDrawable != null) {
            lineDrawable.setBounds(left, top, right, bottom);
            lineDrawable.draw(canvas);
        }
    }

    /**
     * 画竖直线
     * @param child
     */
    protected void drawVerticalLine(Canvas canvas, View child) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        int left = child.getRight() + params.rightMargin;
        int right = child.getRight() + params.rightMargin + lineWidth;
        int top = child.getTop() - params.topMargin;
        int bottom = child.getBottom() + params.bottomMargin + lineWidth;
        if (lineColor != 0) {
            canvas.drawRect(left, top, right, bottom, paint);
        } else if (lineDrawable != null) {
            lineDrawable.setBounds(left, top, right, bottom);
            lineDrawable.draw(canvas);
        }
    }

    /**
     * 获取线宽
     * @return
     */
    public int getLineWidth() {
        return lineWidth;
    }

    /**
     * 获取线颜色
     * @return
     */
    public int getLineColor() {
        return lineColor;
    }

    /**
     * 获取线Drawable
     * @return
     */
    public Drawable getLineDrawable() {
        return lineDrawable;
    }

}
