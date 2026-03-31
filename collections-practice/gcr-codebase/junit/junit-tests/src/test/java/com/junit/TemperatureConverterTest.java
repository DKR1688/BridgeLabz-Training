package com.junit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class TemperatureConverterTest {

    @Test
    void celsiusToFahrenheitTest() {
        assertEquals(32.0, TemperatureConverter.celsiusToFahrenheit(0));
        assertEquals(212.0, TemperatureConverter.celsiusToFahrenheit(100));
    }

    @Test
    void fahrenheitToCelsiusTest() {
        assertEquals(0.0, TemperatureConverter.fahrenheitToCelsius(32));
        assertEquals(100.0, TemperatureConverter.fahrenheitToCelsius(212));
    }
}