package com.example.lap10255.exampleroom.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lap10255.exampleroom.Activity.PetActivity;
import com.example.lap10255.exampleroom.Database.MyDb;
import com.example.lap10255.exampleroom.Model.User;
import com.example.lap10255.exampleroom.R;

import java.util.ArrayList;
import java.util.Collections;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    ArrayList<User> listUser;
    Context ctx;

    public UserAdapter(Context ctx) {
        listUser = new ArrayList<>();
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = listUser.get(position);
        String name = user.getName();
        String sex = user.getSex();
        int age = user.getAge();

        holder.txtUserName.setText(name);
        holder.txtSex.setText(sex);
        holder.txtAge.setText(String.valueOf(age));

        holder.itemView.setOnLongClickListener(v -> {
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ctx);
            builder.setTitle("Delete user?");
            builder.setPositiveButton("Yes", (dialog, which) -> deleteUser(user));

            builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());

            builder.show();
            return true;
        });

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(ctx.getApplicationContext(), PetActivity.class);
            i.putExtra("userId", user.getId());
            ctx.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public void setListUser(ArrayList<User> listUser) {
        this.listUser = new ArrayList<>(listUser);
        notifyDataSetChanged();
    }

    public void add(User user) {
        this.listUser.add(user);
        Collections.sort(this.listUser, (user1, user2) -> user1.getName().compareTo(user2.getName()));
        notifyDataSetChanged();
    }

    @SuppressLint("CheckResult")
    private void deleteUser(User user) {
        Single.create(emitter -> emitDelete(emitter, user))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::deleteUserFromUI, this::handleDeletingError);
    }

    private void emitDelete(SingleEmitter<Object> emitter, User user) {
        MyDb.getDbInstance(ctx).userDAO().delete(user);
        emitter.onSuccess(user);
    }

    private void deleteUserFromUI(Object o) {
        User user = (User) o;
        listUser.remove(user);
        notifyDataSetChanged();
        Toast.makeText(ctx, "Deleted", Toast.LENGTH_SHORT).show();
    }

    private void handleDeletingError(Throwable e) {
        Toast.makeText(ctx, e.toString(), Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }

    public void clear() {
        this.listUser.clear();
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);

            this.txtUserName = itemView.findViewById(R.id.txtUserName);
            this.txtSex = itemView.findViewById(R.id.txtSex);
            this.txtAge = itemView.findViewById(R.id.txtAge);
        }

        TextView txtUserName;
        TextView txtSex;
        TextView txtAge;

    }
}
