package com.example.TimeAttendanceAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateInfo {
    private LocalDate date;
    private LocalTime firstCheckIn;
    private double totalInHours = 0;
    private boolean haveAbsent;
    private boolean absentApproved;
    private boolean holiday;

    public void addTotalHours(double amount) {
        totalInHours+= amount;
    }
}
