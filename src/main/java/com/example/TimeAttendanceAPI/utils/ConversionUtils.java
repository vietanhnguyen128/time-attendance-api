package com.example.TimeAttendanceAPI.utils;

import com.example.TimeAttendanceAPI.model.DateOfMonth;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConversionUtils {
    public static DateOfMonth constructLocalDate(int month, int year) {
        String startDate = "01/";
        String endDate;

        if (month < 10) {
            startDate = startDate + "0" + month + "/" + year;
        } else {
            startDate = startDate + month + "/" + year;
        }

        LocalDate convertedDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        int endOfMonth = convertedDate.withDayOfMonth(convertedDate.getMonth().length(convertedDate.isLeapYear())).getDayOfMonth();

        if (endOfMonth < 10) {
            endDate = "0" + endOfMonth + startDate.substring(2);
        } else {
            endDate = endOfMonth + startDate.substring(2);
        }

        return new DateOfMonth(startDate, endDate);
    }
}