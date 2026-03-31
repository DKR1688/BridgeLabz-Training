package com.junit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class DateFormatter {

    //Converting yyyy-MM-dd to dd-MM-yyyy
    public static String formatDate(String inputDate) {
        if (inputDate==null || inputDate.isEmpty()) {
            return null;
        }
        try {
            LocalDate date = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}