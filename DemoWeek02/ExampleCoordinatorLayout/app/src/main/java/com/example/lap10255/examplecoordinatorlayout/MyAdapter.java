package com.example.lap10255.examplecoordinatorlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<String> listItem;
    private Context ctx;

    public MyAdapter(Context ctx) {
        this.listItem = new ArrayList<>(Arrays.asList(ctx.getResources().getStringArray(R.array.list)));
        this.ctx = ctx;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtItem;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtItem = itemView.findViewById(R.id.txtItem);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.txtItem.setText(listItem.get(position));
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
