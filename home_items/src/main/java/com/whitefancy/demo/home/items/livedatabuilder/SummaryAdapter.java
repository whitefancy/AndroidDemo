package com.whitefancy.demo.home.items.livedatabuilder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whitefancy.demo.home.items.R;
import com.whitefancy.demo.home.items.roomDB.Summary;

import java.util.List;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.MyViewHolder> {
    private Context context;
    private List<Summary> items;

    public SummaryAdapter(Context context, List<Summary> data) {
        this.items = data;
        this.context = context;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.my_summary_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull SummaryAdapter.MyViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateData(List<Summary> all) {
        this.items = all;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView count;

        public MyViewHolder(@NonNull @org.jetbrains.annotations.NotNull View my_summary_row) {
            super(my_summary_row);
            count = my_summary_row.findViewById(R.id.item_count);
        }

        void bindData(int position) {
            Summary contact = items.get(position);
            String ans = contact.type + ": " + contact.count + " 件";
            count.setText(ans);
        }

    }


}
