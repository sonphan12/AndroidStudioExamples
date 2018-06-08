package com.example.lap10255.exampleretrofit.Components;

import com.example.lap10255.exampleretrofit.JobDetailActivity;
import com.example.lap10255.exampleretrofit.Modules.EventBusModule;

import dagger.Component;

@Component(modules = EventBusModule.class)
public interface JobDetailComponent {
    void injectJobDetailActivity(JobDetailActivity jobDetailActivity);
}
