package com.example.lap10255.exampleretrofit.Modules;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;

@Module
public class LinearLayoutManagerModule {
    Context context;

    public LinearLayoutManagerModule(Context context) {
        this.context = context;
    }

    @Provides
    public LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(context);
    }
}
