package com.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
	@Test
    void testAddition() {
        assertEquals(5, Calculator.add(2, 3));
        System.out.println("Addition is- "+Calculator.add(2, 3));
    }

    @Test
    void testSubtraction() {
        assertEquals(1, Calculator.subtract(3, 2));
    }

    @Test
    void testMultiplication() {
        assertEquals(6, Calculator.multiply(2, 3));
    }

    @Test
    void testDivision() {
        assertEquals(2, Calculator.divide(6, 3));
    }

    @Test
    void testDivisionByZero() {
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            Calculator.divide(5, 0);
        });
        assertEquals("Division by zero is not allowed.", exception.getMessage());
    }
}
