package com.example.lap10255.examplerxjava2loaddata;

public class CustomPerson extends Person {
    private String gender;

    public CustomPerson(String name, int age) {
        super(name, age);
        gender = "Male";
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
