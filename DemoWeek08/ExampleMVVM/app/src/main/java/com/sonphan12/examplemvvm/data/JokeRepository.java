package com.sonphan12.examplemvvm.data;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.sonphan12.examplemvvm.data.model.Joke;
import com.sonphan12.examplemvvm.data.model.JokeList;
import com.sonphan12.examplemvvm.data.remote.JokeService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JokeRepository {
    JokeService jokeService;
    List<Joke> listJoke;
    MutableLiveData<JokeList> data;

    public JokeRepository(JokeService jokeService) {
        this.jokeService = jokeService;
        listJoke = new ArrayList<>();
        if (data == null) data = new MutableLiveData<>();
    }


    public LiveData<JokeList> getListJoke() {
        jokeService.getTenRandomJoke().enqueue(new Callback<JokeList>() {
            @Override
            public void onResponse(Call<JokeList> call, Response<JokeList> response) {
                if (response.isSuccessful()) data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<JokeList> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return data;
    }
}
