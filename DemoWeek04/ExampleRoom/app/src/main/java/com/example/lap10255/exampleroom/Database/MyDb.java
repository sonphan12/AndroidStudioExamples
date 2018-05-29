package com.example.lap10255.exampleroom.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.lap10255.exampleroom.Model.Pet;
import com.example.lap10255.exampleroom.Model.User;

@Database(entities = {User.class, Pet.class}, version = 2)
public abstract class MyDb extends RoomDatabase {
    private static MyDb INSTANCE;

    public abstract UserDAO userDAO();

    public abstract PetDAO petDAO();

    public static MyDb getDbInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    MyDb.class, "my-db").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
}
