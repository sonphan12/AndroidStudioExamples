package com.sonphan12.examplemvvm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sonphan12.examplemvvm.data.remote.JokeClient;
import com.sonphan12.examplemvvm.data.model.JokeList;
import com.sonphan12.examplemvvm.data.JokeRepository;
import com.sonphan12.examplemvvm.data.remote.JokeService;

public class ListJokeViewModel extends ViewModel {
    private LiveData<JokeList> listJoke;
    private JokeRepository repository;

    public void init() {
        requestListJob();
    }

    public void requestListJob(){
        if (repository == null) {
            JokeService jokeService = JokeClient.getInstance().create(JokeService.class);
            repository = new JokeRepository(jokeService);
        }
        listJoke = repository.getListJoke();
    }

    public LiveData<JokeList> getListJob() {
        return listJoke;
    }
}
