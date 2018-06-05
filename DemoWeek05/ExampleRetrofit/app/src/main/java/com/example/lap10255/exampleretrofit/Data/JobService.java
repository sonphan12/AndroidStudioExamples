package com.example.lap10255.exampleretrofit.Data;

import com.example.lap10255.exampleretrofit.Model.Job;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JobService {
    @GET("/positions.json")
    Call<ArrayList<Job>> getAllJobs();

    @GET("/positions.json")
    Call<ArrayList<Job>> getJobFromInfo(@Query("description") String description,
                                        @Query("location") String location,
                                        @Query("full_time") Boolean fullTime);
}
