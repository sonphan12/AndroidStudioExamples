package com.example.lap10255.sqliteexample;

public class Info {
    private long id;
    private String name;
    private int age;

    public Info(String name, int age) {
        this.id = -1;
        this.name = name;
        this.age = age;
    }

    public Info(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Info() {
        name = "Unknown";
        age = 10;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
