package com.example.lap10255.exampleretrofit.Modules;


import com.example.lap10255.exampleretrofit.Data.JobService;
import com.example.lap10255.exampleretrofit.Util.Util;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class JobServiceModule {
    public JobServiceModule() {
    }

    @Provides
//    @Singleton
    public JobService getJobService() {
        return Util.getJobService();
    }
}
