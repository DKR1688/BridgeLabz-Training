package com.junit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class PasswordValidatorTest {

    @Test
    void validPasswordTest() {
        assertTrue(PasswordValidator.isValid("Hello123"));
    }

    @Test
    void tooShortPasswordTest() {
        assertFalse(PasswordValidator.isValid("Ab1"));
    }

    @Test
    void noUppercasePasswordTest() {
        assertFalse(PasswordValidator.isValid("password123"));
    }

    @Test
    void testNoDigitPassword() {
        assertFalse(PasswordValidator.isValid("Password"));
    }

    @Test
    void testNullPassword() {
        assertFalse(PasswordValidator.isValid(null));
    }
}