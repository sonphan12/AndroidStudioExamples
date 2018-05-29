package com.example.lap10255.exampleroom.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lap10255.exampleroom.Model.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM users ORDER BY users.name ASC")
    Flowable<List<User>> getAll();

    @Query("SELECT * FROM users WHERE name LIKE '%' || :name || '%' ORDER BY users.name ASC")
    Flowable<List<User>> findByName(String name);

    @Query("SELECT * FROM users WHERE id = :id")
    Flowable<User> findById(int id);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}
