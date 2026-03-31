package com.junit;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class StringUtilityMethodsTest {
	@Test
    void testReverse() {
        assertEquals("olleh", StringUtilityMethods.reverse("hello"));
        assertNull(StringUtilityMethods.reverse(null));
    }

    @Test
    void testIsPalindrome() {
        assertTrue(StringUtilityMethods.isPalindrome("madam"));
        assertTrue(StringUtilityMethods.isPalindrome("RaceCar"));
        assertFalse(StringUtilityMethods.isPalindrome(null));
    }

    @Test
    void testToUpperCase() {
        assertEquals("HELLO", StringUtilityMethods.toUpperCase("hello"));
        assertNull(StringUtilityMethods.toUpperCase(null));
    }
}
