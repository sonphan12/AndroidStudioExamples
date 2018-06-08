package com.example.lap10255.exampleretrofit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lap10255.exampleretrofit.Model.Job;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {
    private Context ctx;
    private ArrayList<Job> listJobs;
    public JobAdapter(Context ctx) {
        this.ctx = ctx;
        this.listJobs = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.job_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Job job = listJobs.get(position);
        Glide.with(ctx).load(job.getCompanyLogo()).into(holder.imgLogo);
        holder.txtTitle.setText(job.getTitle());
        holder.txtType.setText(job.getType());
        holder.txtLocation.setText(job.getLocation());
        holder.txtCompany.setText(job.getCompany());
        holder.txtCreatedAt.setText(job.getCreatedAt());

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(ctx, JobDetailActivity.class);
            ctx.startActivity(i);
            EventBus.getDefault().postSticky(job);
        });
    }

    @Override
    public int getItemCount() {
        return listJobs.size();
    }

    public void setListJobs(ArrayList<Job> listJobs) {
        this.listJobs = new ArrayList<>(listJobs);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo;
        TextView txtTitle;
        TextView txtType;
        TextView txtLocation;
        TextView txtCompany;
        TextView txtCreatedAt;

        ViewHolder(@NonNull View v) {
            super(v);
            this.imgLogo = v.findViewById(R.id.imgLogo);
            this.txtTitle = v.findViewById(R.id.txtTitle);
            this.txtType = v.findViewById(R.id.txtType);
            this.txtLocation = v.findViewById(R.id.txtLocation);
            this.txtCompany = v.findViewById(R.id.txtCompany);
            this.txtCreatedAt = v.findViewById(R.id.txtCreatedAt);
        }
    }
}
