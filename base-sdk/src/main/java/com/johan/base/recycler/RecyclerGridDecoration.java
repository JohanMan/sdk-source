package com.johan.base.recycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by johan on 2018/6/5.
 */

public class RecyclerGridDecoration extends RecyclerDecoration {

    public RecyclerGridDecoration(int lineWidth, int lineColor) {
        super(lineWidth, lineColor);
    }

    public RecyclerGridDecoration(Drawable lineDrawable) {
        super(lineDrawable);
    }

    public RecyclerGridDecoration(Context context, int drawableId) {
        super(context, drawableId);
    }

    public RecyclerGridDecoration(Context context) {
        super(context);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            GridLayoutManager layoutManager = (GridLayoutManager) manager;
            int spanCount = layoutManager.getSpanCount();
            int count = layoutManager.getItemCount();
            int current = parent.getChildAdapterPosition(view);
            int left = 0;
            int right = getLineWidth();
            int top = 0;
            int bottom = getLineWidth();
            // 最后一列
            if (current % spanCount == spanCount - 1) {
                right = 0;
            }
            // 最后一行
            if ((current / spanCount) == (count / spanCount) - 1) {
                bottom = 0;
            }
            outRect.set(left, top, right, bottom);
        }
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                drawVerticalLine(canvas, child);
                drawHorizontalLine(canvas, child);
            }
        }
    }

}
