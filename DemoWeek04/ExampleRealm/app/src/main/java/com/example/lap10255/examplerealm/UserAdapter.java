package com.example.lap10255.examplerealm;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.Sort;

public class UserAdapter extends RealmBaseAdapter<User> implements ListAdapter {
    private Context ctx;

    private static class ViewHolder {
        TextView txtName;
        TextView txtSex;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        User user = adapterData.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.user_item, parent, false);

            holder = new ViewHolder();
            holder.txtName = convertView.findViewById(R.id.txtName);
            holder.txtSex = convertView.findViewById(R.id.txtSex);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (adapterData != null) {
            holder.txtName.setText(user.getName());
            holder.txtSex.setText(user.getSex());
        }

        convertView.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
            builder.setTitle("Delete user?")
                    .setMessage("Do you want to delete this user?");

            builder.setPositiveButton("Yes", ((dialog, which) -> deleteUser(user)));
            builder.setNegativeButton("No", ((dialog, which) -> dialog.cancel()));

            builder.show();

            return true;
        });

        convertView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
            builder.setTitle("Update user");

            LinearLayout layout = new LinearLayout(v.getContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(10, 10, 10, 10);

            TextView txtName = new TextView(v.getContext());
            txtName.setText(R.string.name);

            EditText edtName = new EditText(v.getContext());
            edtName.setText(user.getName());
            edtName.setSelection(edtName.getText().length());

            TextView txtSex = new TextView(v.getContext());
            txtSex.setText(R.string.sex);

            EditText edtSex = new EditText(v.getContext());
            edtSex.setText(user.getSex());

            layout.addView(txtName);
            layout.addView(edtName);
            layout.addView(txtSex);
            layout.addView(edtSex);

            builder.setView(layout);

            builder.setPositiveButton("Update", ((dialog, which) -> {
                User newUser = new User(edtName.getText().toString(), edtSex.getText().toString());
                updateUser(user, newUser);
            }));
            builder.setNegativeButton("Cancel", ((dialog, which) -> dialog.cancel()));

            builder.show();
        });

        return convertView;
    }

    private void updateUser(User user, User newUser) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        user.setName(newUser.getName());
        user.setSex(newUser.getSex());
        realm.commitTransaction();
    }

    private void deleteUser(User user) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        user.deleteFromRealm();
        realm.commitTransaction();
    }


    UserAdapter(Context ctx, @Nullable OrderedRealmCollection<User> data) {
        super(data);
        this.ctx = ctx;
    }
}
