package com.sonphan12.exampleviper.entity;

import java.util.Random;

public class UserRepository {
    // Get mock user
    public static User getCurrentUser() {
        if (new Random().nextInt(3) == 0) return null;
        else return new User("son", "21");
    }
}
