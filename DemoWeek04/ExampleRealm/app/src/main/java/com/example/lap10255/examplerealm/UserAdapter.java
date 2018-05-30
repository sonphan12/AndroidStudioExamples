package com.example.lap10255.examplerealm;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class UserAdapter extends RealmBaseAdapter<User> implements ListAdapter {
    private Context ctx;

    private static class ViewHolder {
        TextView txtName;
        TextView txtSex;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
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
            User user = adapterData.get(position);
            holder.txtName.setText(user.getName());
            holder.txtSex.setText(user.getSex());
        }

        return convertView;
    }

    public UserAdapter(Context ctx, @Nullable OrderedRealmCollection<User> data) {
        super(data);
        this.ctx = ctx;
    }
}
