package com.johan.base.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by johan on 2018/6/5.
 */

public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    private static final int DEFAULT_VIEW_TYPE = 1;

    protected Context context;
    protected List<T> dataList;

    private SparseArray<Integer> layouts = new SparseArray<>();
    private HashMap<Integer, ItemViewTypeFilter> filters = new HashMap<>();
    private SparseArray<ItemViewProcessor<T>> processors = new SparseArray<>();

    public RecyclerAdapter(Context context, List<T> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int index = layouts.indexOfKey(viewType);
        if (index < 0) {
            throw new RuntimeException("RecyclerAdapter can not find layout with viewType as " + viewType);
        }
        int layoutId = layouts.get(viewType);
        View layout = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new RecyclerViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        int index = processors.indexOfKey(viewType);
        if (index < 0) {
            throw new RuntimeException("RecyclerAdapter can not find processors with viewType as " + viewType);
        }
        ItemViewProcessor processor = processors.get(viewType);
        processor.onProcess(holder, position, dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        for (Map.Entry<Integer, ItemViewTypeFilter> entry : filters.entrySet()) {
            if (entry.getValue().filter(position)) {
                return entry.getKey();
            }
        }
        return super.getItemViewType(position);
    }

    /**
     * 设置 用于ItemView只有一种布局
     * @param layoutId
     * @param processor
     */
    public void setLayout(int layoutId, ItemViewProcessor<T> processor) {
        ItemViewTypeFilter defaultHunter = new ItemViewTypeFilter() {
            @Override
            public boolean filter(int position) {
                return true;
            }
        };
        addLayout(DEFAULT_VIEW_TYPE, layoutId, defaultHunter, processor);
    }

    /**
     * 添加 用于ItemView有多种布局
     * @param viewType
     * @param layoutId
     * @param hunter
     * @param processor
     */
    public void addLayout(int viewType, int layoutId, ItemViewTypeFilter hunter, ItemViewProcessor<T> processor) {
        layouts.put(viewType, layoutId);
        filters.put(viewType, hunter);
        processors.put(viewType, processor);
    }

    /**
     * ItemView Type过滤器
     */
    public interface ItemViewTypeFilter {
        boolean filter(int position);
    }

    /**
     * ItemView 处理器
     */
    public interface ItemViewProcessor<T> {
        void onProcess(RecyclerViewHolder holder, int position, T data);
    }

}
