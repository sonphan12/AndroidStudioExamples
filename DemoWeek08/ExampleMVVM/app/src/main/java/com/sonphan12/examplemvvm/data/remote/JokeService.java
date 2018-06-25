package com.sonphan12.examplemvvm.data.remote;


import com.sonphan12.examplemvvm.data.model.JokeList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JokeService {
    @GET("jokes/random/10")
    Call<JokeList> getTenRandomJoke();

}
