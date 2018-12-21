package com.brook.app.android.supportlibrary;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brook
 * @time 2018/8/6 18:21
 * @target 666Yaojin
 */
public class RecyclerViewTestAdapter extends RecyclerView.Adapter<RecyclerViewTestAdapter.StringViewHolder> {

    private List<String> data = new ArrayList<>();

    public RecyclerViewTestAdapter() {
        for (int i = 0; i < 50; i++) {
            data.add("数据" + i);
        }
    }

    @NonNull
    @Override
    public StringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StringViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.support_base_item_rv_simple_string, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StringViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    class StringViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public StringViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}
