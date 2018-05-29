package com.example.lap10255.exampleroom.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "pets",
        foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"
                    , onDelete = CASCADE))
public class Pet {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String species;
    private int userId;

    public Pet(String name, String species, int userId) {
        this.name = name;
        this.species = species;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
