package com.junit;

public class UserRegistration {
    public static boolean registerUser(String username, String email, String password) {
        if (username==null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid username.");
        }
        if (email==null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email.");
        }
        if (password==null || password.length() < 6) {
            throw new IllegalArgumentException("Invalid password.");
        }
        //if all validations pass then we will return true
        return true;
    }
}