package com.junit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class UserRegistrationTest {

    @Test
    void validRegistrationTest() {
        assertTrue(UserRegistration.registerUser("Deepak", "deepak@gmail.com", "secret123"));
    }

    @Test
    void invalidUsernameTest() {
        assertThrows(IllegalArgumentException.class, () -> {
        	UserRegistration.registerUser("", "deepak@gmail.com", "secret123");});
    }

    @Test
    void invalidEmailTest() {
        assertThrows(IllegalArgumentException.class, () -> {
        	UserRegistration.registerUser("Deepak", "deepakgmail.com", "secret123");});
    }

    @Test
    void invalidPasswordTest() {
        assertThrows(IllegalArgumentException.class, () -> {
        	UserRegistration.registerUser("Deepak", "deepak@gmail.com", "123");});
    }

    @Test
    void nullInputsTest() {
        assertThrows(IllegalArgumentException.class, () -> {
        	UserRegistration.registerUser(null, "deepak@gmail.com", "secret123");});

        assertThrows(IllegalArgumentException.class, () -> {
        	UserRegistration.registerUser("Deepak", null, "secret123");});
    }
}