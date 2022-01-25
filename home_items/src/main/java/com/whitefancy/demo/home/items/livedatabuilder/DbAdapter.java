package com.whitefancy.demo.home.items.livedatabuilder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whitefancy.demo.home.items.R;
import com.whitefancy.demo.home.items.roomDB.Item;

import java.util.List;

public class DbAdapter extends RecyclerView.Adapter<DbAdapter.MyViewHolder> {
    private Context context;
    private List<Item> items;
    private ActionCallback mActionCallbacks;

    //Interface for callbacks
    interface ActionCallback {
        void onLongClickListener(Item contact);
    }

    public DbAdapter(Context context, List<Item> data) {
        this.items = data;
        this.context = context;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.my_table_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull DbAdapter.MyViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateData(List<Item> all) {
        this.items = all;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public View table_row;
        private TextView name;
        private TextView type;
        private TextView place;
        private TextView time;
        private ImageView imageView;

        public MyViewHolder(@NonNull @org.jetbrains.annotations.NotNull View my_table_row) {
            super(my_table_row);
            my_table_row.setOnLongClickListener(this);
            table_row = my_table_row;
            name = my_table_row.findViewById(R.id.item_name);
            type = my_table_row.findViewById(R.id.item_type);
            place = my_table_row.findViewById(R.id.item_place);
            imageView = my_table_row.findViewById(R.id.item_image);
            time = my_table_row.findViewById(R.id.item_status);
        }

        void bindData(int position) {
            Item contact = items.get(position);
            name.setText(contact.name);
            type.setText(contact.type);
            place.setText(contact.place);
            name.setText(contact.name);
            setPic(contact.imgUrl);
            int date = (int) (contact.createTs + contact.lifetime - System.currentTimeMillis() / 1000) / (24 * 60 * 60) + 1;
            time.setText(date + " 天");
        }

        private void setPic(String currentPhotoPath) {
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            imageView.setImageBitmap(bitmap);
        }

        @Override
        public boolean onLongClick(View view) {
            if (mActionCallbacks != null) {
                mActionCallbacks.onLongClickListener(items.get(getAdapterPosition()));
            }
            return true;
        }


    }

    void addActionCallback(ActionCallback actionCallbacks) {
        mActionCallbacks = actionCallbacks;
    }
}
