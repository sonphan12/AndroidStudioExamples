package com.example.lap10255.examplecoordinatorlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    RecyclerView recycler1;
    RecyclerView recycler2;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler1 = findViewById(R.id.recycle1);
        recycler2 = findViewById(R.id.recycle2);
        adapter = new MyAdapter(this);

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);
        recycler1.setLayoutManager(layoutManager1);
        recycler1.setItemAnimator(new DefaultItemAnimator());
        recycler1.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this);
        recycler2.setLayoutManager(layoutManager2);
        recycler2.setItemAnimator(new DefaultItemAnimator());
        recycler2.setAdapter(adapter);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        recycler1.setAdapter(arrayAdapter);

    }
}
