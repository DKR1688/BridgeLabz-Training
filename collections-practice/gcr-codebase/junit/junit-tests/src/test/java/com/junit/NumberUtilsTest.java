package com.junit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberUtilsTest {
	// Test with even numbers
    @ParameterizedTest
    @ValueSource(ints ={2, 4, 6})
    public void isEvenTest(int number) {
        assertTrue(NumberUtils.isEven(number));
    }

    // Test with odd numbers
    @ParameterizedTest
    @ValueSource(ints ={7, 9})
    public void testIsEvenWithOddNumbers(int number) {
    	assertFalse(NumberUtils.isEven(number));
    }

}
