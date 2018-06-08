package com.johan.base.recycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by johan on 2018/6/5.
 */

public class RecyclerListDecoration extends RecyclerDecoration {

    public RecyclerListDecoration(int lineWidth, int lineColor) {
        super(lineWidth, lineColor);
    }

    public RecyclerListDecoration(Drawable lineDrawable) {
        super(lineDrawable);
    }

    public RecyclerListDecoration(Context context, int drawableId) {
        super(context, drawableId);
    }

    public RecyclerListDecoration(Context context) {
        super(context);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) manager;
            int last = manager.getItemCount() - 1;
            int current = parent.getChildLayoutPosition(view);
            if (current == -1) return;
            if (last == current) {
                // 最后一行/一列
                outRect.set(0, 0, 0, 0);
                return;
            }
            int orientation = layoutManager.getOrientation();
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                outRect.set(0, 0, getLineWidth(), 0);
            } else if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.set(0, 0, 0, getLineWidth());
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) manager;
            int orientation = layoutManager.getOrientation();
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                if (orientation == LinearLayoutManager.HORIZONTAL) {
                    drawVerticalLine(canvas, child);
                } else if (orientation == LinearLayoutManager.VERTICAL) {
                    drawHorizontalLine(canvas, child);
                }
            }
        }
    }

}
