package com.example.lap10255.exampleretrofit.Modules;

import android.content.Context;

import com.example.lap10255.exampleretrofit.JobAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class JobAdapterModule {
    Context context;

    public JobAdapterModule(Context context) {
        this.context = context;
    }

    @Provides
    public JobAdapter getJobAdapter() {
        return new JobAdapter(context);
    }
}
