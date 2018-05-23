package com.example.lap10255.examplegreendao.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
    @Id(autoincrement = true)
    private Long Id;

    @Property
    private String name;

    @Property
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User() {
        this.name = "Unknown";
        this.age = 18;
    }

    @Generated(hash = 1616662873)
    public User(Long Id, String name, int age) {
        this.Id = Id;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return Id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }
}
