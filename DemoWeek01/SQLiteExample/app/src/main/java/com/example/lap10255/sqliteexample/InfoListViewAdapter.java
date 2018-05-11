package com.example.lap10255.sqliteexample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class InfoListViewAdapter extends ArrayAdapter<Info> {
    private ArrayList<Info> listInfo;
    private Context ctx;
    private int resource;
    private MyDBHelper myDBHelper;

    @Override
    public void add(@Nullable Info object) {
        super.add(object);
        listInfo.add(object);
    }

    public InfoListViewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Info> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.resource = resource;
        this.listInfo = new ArrayList<>(objects);
        myDBHelper = new MyDBHelper(ctx);
    }

    @Nullable
    @Override
    public Info getItem(int position) {
        return listInfo.get(position);
    }

    @Override
    public int getCount() {
        return listInfo.size();
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        convertView = inflater.inflate(resource, parent, false);
        Info i = getItem(position);
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtAge = convertView.findViewById(R.id.txtAge);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDBHelper.deleteData(getItem(position).getId());
                listInfo.remove(position);
                notifyDataSetChanged();
            }
        });

        if (i != null) {
            txtName.setText(i.getName());
            txtAge.setText(String.valueOf(i.getAge()));
        }


        return convertView;
    }

    public ArrayList<Info> getListInfo() {
        return listInfo;
    }
}
