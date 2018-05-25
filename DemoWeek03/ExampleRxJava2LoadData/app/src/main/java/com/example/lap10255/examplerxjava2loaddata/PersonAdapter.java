package com.example.lap10255.examplerxjava2loaddata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

public class PersonAdapter extends ArrayAdapter<Person> {
    private Context context;
    private int resource;
    private ArrayList<Person> listPerson;
    public PersonAdapter(@NonNull Context context, int resource, ArrayList<Person> listPerson) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.listPerson = new ArrayList<>(listPerson);
    }

    @Override
    public void add(@Nullable Person object) {
        listPerson.add(object);
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        listPerson.clear();
        notifyDataSetChanged();
    }

    @Override
    public void addAll(@NonNull Collection<? extends Person> collection) {
        listPerson.addAll(collection);
        notifyDataSetChanged();
    }


    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.person_item, parent, false);
        Person person = listPerson.get(position);
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtAge = convertView.findViewById(R.id.txtAge);
        if (person instanceof CustomPerson) {
            TextView txtGender = convertView.findViewById(R.id.txtGender);
            txtGender.setText(((CustomPerson)person).getGender());
        }
        txtName.setText(person.getName());
        txtAge.setText(String.valueOf(person.getAge()));
        return convertView;
    }

    @Nullable
    @Override
    public Person getItem(int position) {
        return listPerson.get(position);
    }

    @Override
    public int getCount() {
        return listPerson.size();
    }


}
