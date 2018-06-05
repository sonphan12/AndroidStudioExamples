package com.example.lap10255.exampleretrofit.Util;

import com.example.lap10255.exampleretrofit.Data.JobService;
import com.example.lap10255.exampleretrofit.Remote.RetrofitClient;

public class Util {
    public static final String BASE_URL = "https://jobs.github.com";
    public static JobService getJobService() {
        return RetrofitClient.getInstance(BASE_URL).create(JobService.class);
    }
}
