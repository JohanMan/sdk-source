package com.johan.sdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.johan.base.recycler.RecyclerAdapter;
import com.johan.base.recycler.RecyclerListDecoration;
import com.johan.base.recycler.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//      GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            dataList.add(i % 2 == 0 ? "name" + i : "content" + i);
        }
        RecyclerAdapter<String> adapter = new RecyclerAdapter<>(this, dataList);
        adapter.setLayout(R.layout.item_recycler, processor);
        recyclerView.setAdapter(adapter);
//      recyclerView.addItemDecoration(new RecyclerGridDecoration(this));
        recyclerView.addItemDecoration(new RecyclerListDecoration(this));
    }

    private RecyclerAdapter.ItemViewProcessor<String> processor = new RecyclerAdapter.ItemViewProcessor<String>() {
        @Override
        public void onProcess(RecyclerViewHolder holder, int position, String data) {
            TextView nameView = holder.getView(R.id.name_view);
            nameView.setText(data);
        }
    };

}
