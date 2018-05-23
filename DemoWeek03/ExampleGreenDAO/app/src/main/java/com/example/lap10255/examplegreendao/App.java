package com.example.lap10255.examplegreendao;

import android.app.Application;

import com.example.lap10255.examplegreendao.Model.DaoMaster;
import com.example.lap10255.examplegreendao.Model.DaoSession;

import org.greenrobot.greendao.database.Database;

public class App extends Application {
    DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "user-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
