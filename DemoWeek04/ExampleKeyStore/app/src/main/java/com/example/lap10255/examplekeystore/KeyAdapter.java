package com.example.lap10255.examplekeystore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class KeyAdapter extends ArrayAdapter<String> {
    Context ctx;
    ArrayList<String> listAlias;

    public KeyAdapter(Context ctx) {
        super(ctx, R.layout.key_item);
        this.ctx = ctx;
        listAlias = new ArrayList<>();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return listAlias.get(position);
    }

    @Override
    public int getCount() {
        return listAlias.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder") View v = LayoutInflater.from(ctx).inflate(R.layout.key_item, parent, false);
        String alias = getItem(position);
        TextView txtAlias = v.findViewById(R.id.txtAlias);
        if (alias != null) {
            txtAlias.setText(alias);
        }

        Button btnDelete = v.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(view -> {
            MainActivity activity = (MainActivity) ctx;
            Observable.create(emitter -> {
                activity.deleteKey(alias);
                emitter.onNext(true);
                emitter.onComplete();
            }).subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(op -> activity.refreshKeys(), Throwable::printStackTrace);
        });

        Button btnEncrypt = v.findViewById(R.id.btnEncrypt);
        btnEncrypt.setOnClickListener(view -> {
            MainActivity activity = (MainActivity) ctx;
            Observable.create(emitter -> {
                emitter.onNext(activity.encryptString(alias));
                emitter.onComplete();
            })
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(val -> activity.txtEncrypted.setText((String) val), Throwable::printStackTrace);
        });

        Button btnDecrypt = v.findViewById(R.id.btnDecrypt);
        btnDecrypt.setOnClickListener(view -> {
            MainActivity activity = (MainActivity) ctx;
            Observable.create(emitter -> {
                emitter.onNext(activity.decryptString(alias));
                emitter.onComplete();
            })
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(val -> activity.txtDecrypted.setText((String) val), Throwable::printStackTrace);
        });

        return v;
    }


    public void setListAlias(ArrayList<String> listAlias) {
        if (this.listAlias == null) {
            this.listAlias = new ArrayList<>();
        }
        this.listAlias = listAlias;
        notifyDataSetChanged();
    }
}
