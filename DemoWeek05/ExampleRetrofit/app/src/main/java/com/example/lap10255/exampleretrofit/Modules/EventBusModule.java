package com.example.lap10255.exampleretrofit.Modules;

import org.greenrobot.eventbus.EventBus;

import dagger.Module;
import dagger.Provides;

@Module
public class EventBusModule {
    public EventBusModule() {
    }

    @Provides
    public EventBus getEventBus() {
        return EventBus.getDefault();
    }
}
