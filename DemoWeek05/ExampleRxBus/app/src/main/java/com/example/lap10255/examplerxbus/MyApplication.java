package com.example.lap10255.examplerxbus;

import android.app.Application;

public class MyApplication extends Application {
    private RxBus bus;
    @Override
    public void onCreate() {
        super.onCreate();
        bus = new RxBus();
    }

    public RxBus getBus() {
        return bus;
    }
}
