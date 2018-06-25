package com.sonphan12.examplemvvm.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.sonphan12.examplemvvm.R;
import com.sonphan12.examplemvvm.data.model.Joke;
import com.sonphan12.examplemvvm.viewmodel.ListJokeViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvJoke;
    ProgressBar progressBar;
    Button btnLoad;
    ListJokeViewModel viewModel;
    JokeAdapter jokeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvJoke = findViewById(R.id.rvJoke);
        jokeAdapter = new JokeAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvJoke.setLayoutManager(layoutManager);
        rvJoke.setAdapter(jokeAdapter);

        btnLoad = findViewById(R.id.btnLoad);
        progressBar = findViewById(R.id.progressBar);

        viewModel = ViewModelProviders.of(this).get(ListJokeViewModel.class);
        viewModel.init();

        viewModel.getListJob().observe(this, listJoke -> {
            progressBar.setVisibility(View.GONE);
            jokeAdapter.setListJoke(listJoke.getJokes());
            jokeAdapter.notifyDataSetChanged();
        });

        btnLoad.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            ArrayList<Joke> emptyList = new ArrayList<>();
            jokeAdapter.setListJoke(emptyList);
            jokeAdapter.notifyDataSetChanged();
            viewModel.requestListJob();
        });
    }
}
