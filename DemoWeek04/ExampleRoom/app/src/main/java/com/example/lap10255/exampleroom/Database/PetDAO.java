package com.example.lap10255.exampleroom.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lap10255.exampleroom.Model.Pet;

import java.util.List;

import io.reactivex.Flowable;
@Dao
public interface PetDAO {
    @Query("SELECT * FROM pets WHERE pets.userId = :userId ORDER BY pets.name ASC")
    Flowable<List<Pet>> getAll(int userId);

    @Insert
    void insert(Pet pet);

    @Delete
    void delete(Pet pet);
}
