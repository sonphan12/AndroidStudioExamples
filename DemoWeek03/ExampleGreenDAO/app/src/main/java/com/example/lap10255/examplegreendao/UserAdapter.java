package com.example.lap10255.examplegreendao;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lap10255.examplegreendao.Model.User;
import com.example.lap10255.examplegreendao.Model.UserDao;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private ArrayList<User> listUser;
    private Context context;



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final User user = listUser.get(position);
        holder.txtName.setText(user.getName());
        holder.txtAge.setText(String.valueOf(user.getAge()));
        holder.btnDelete.setOnClickListener(v -> deleteUser(position));

        holder.itemView.setOnClickListener(v -> {
            // Grab the user
            final User user1 = listUser.get(holder.getLayoutPosition());
            // Create Alert Dialog to update
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Update user info");

            // Set up input layout
            final LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            final EditText edtName = new EditText(context);
            final EditText edtAge = new EditText(context);
            edtName.setText(user1.getName());
            edtAge.setText(String.valueOf(user1.getAge()));
            linearLayout.addView(edtName);
            linearLayout.addView(edtAge);
            linearLayout.setPadding(10, 10, 10, 10);
            builder.setView(linearLayout);

            // Set up buttons
            builder.setPositiveButton("Update", (dialog, which) -> {
                // Update operation
                String newName = edtName.getText().toString();
                int newAge = Integer.parseInt(edtAge.getText().toString());
                UserDao userDao = ((App) context.getApplicationContext()).getDaoSession().getUserDao();
                user1.setName(newName);
                user1.setAge(newAge);
                userDao.update(user1);
                notifyDataSetChanged();

            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                // Do nothing
            });
            AlertDialog dialog = builder.show();
            final Button btnUpdate = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

            // If one of two EditText is empty -> Disable "Update" button in the alert dialog
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!edtName.getText().toString().isEmpty() && !edtAge.getText().toString().isEmpty()) {
                        btnUpdate.setEnabled(true);
                    } else {
                        btnUpdate.setEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };

            edtName.addTextChangedListener(textWatcher);
            edtAge.addTextChangedListener(textWatcher);
        });
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public UserAdapter(Context context, ArrayList<User> listUser) {
        this.context = context;
        this.listUser = new ArrayList<>(listUser);
    }

    public void setList(ArrayList<User> listUser) {
        this.listUser = listUser;
        notifyDataSetChanged();
    }

    public ArrayList<User> getList() {
        return listUser;
    }

    public void deleteUser(int position) {
        User user = listUser.get(position);
        UserDao userDao = ((App) context.getApplicationContext()).getDaoSession().getUserDao();
        userDao.delete(user);
        listUser.remove(position);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtAge;
        Button btnDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtAge = itemView.findViewById(R.id.txtAge);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

    }
}
