package com.whitefancy.layoutdemo.MyRecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whitefancy.layoutdemo.R;

//数据适配器，用于管理数据视图
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataset;

    public MyAdapter(String[] mDataset) {
        this.mDataset = mDataset;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        //首先被调用，该方法需要构造一个视图持有者，设置用于显示内容的视图
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull MyAdapter.MyViewHolder holder, int position) {
        //视图持有者构建完成后，布局管理者将其绑定到数据。通过调用数据适配器的onBindViewHolder方法实现
        holder.textView.setText(mDataset[position]);
        final int m = position;
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("text", mDataset[m]);
            }
        });

    }

    //如果列表需要更新，可以在RecyclerView.Adapter对象上调用相应的通知方法，如this.notifyItemChanged(4);
    //此时会重新绑定任何受影响的视图持有者，允许其数据更新。
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyViewHolder(@NonNull @org.jetbrains.annotations.NotNull TextView itemView) {
            super(itemView);
            textView = itemView;
        }
    }
}
