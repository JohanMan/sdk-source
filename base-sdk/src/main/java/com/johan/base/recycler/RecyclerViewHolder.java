package com.johan.base.recycler;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by johan on 2018/6/5.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        views = new SparseArray<>();
    }

    /**
     * 获取View
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int id) {
        View view = views.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            views.put(id, view);
        }
        return (T) view;
    }

}
