package com.example.lap10255.exampleretrofit.Components;


import com.example.lap10255.exampleretrofit.MainActivity;
import com.example.lap10255.exampleretrofit.Modules.JobAdapterModule;
import com.example.lap10255.exampleretrofit.Modules.JobServiceModule;
import com.example.lap10255.exampleretrofit.Modules.LinearLayoutManagerModule;

import dagger.Component;

@Component(modules = { JobServiceModule.class, JobAdapterModule.class, LinearLayoutManagerModule.class})
public interface MainActivityComponent {
    void injectMainActivity(MainActivity mainActivity);
}
