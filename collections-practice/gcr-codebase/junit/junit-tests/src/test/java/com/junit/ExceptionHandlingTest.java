package com.junit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
public class ExceptionHandlingTest {
	@Test
	void divideTest() {
		assertThrows(ArithmeticException.class, 
				() -> {ExceptionHandling.divide(12, 0);});
	}
}
