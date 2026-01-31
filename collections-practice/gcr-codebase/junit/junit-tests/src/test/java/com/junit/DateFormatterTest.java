package com.junit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class DateFormatterTest {

    @Test
    void validDateTest() {
        assertEquals("31-01-2026", DateFormatter.formatDate("2026-01-31"));
    }

    @Test
    void invalidDateFormatTest() {
        assertNull(DateFormatter.formatDate("31-01-2026"));
    }

    @Test
    void invalidDateValueTest() {
        assertNull(DateFormatter.formatDate("2026-02-30"));
    }

    @Test
    void nullInputTest() {
        assertNull(DateFormatter.formatDate(null));
    }
}