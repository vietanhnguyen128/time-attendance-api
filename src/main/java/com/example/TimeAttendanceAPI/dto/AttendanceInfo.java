package com.example.TimeAttendanceAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceInfo {
    private int year;

    private int month;

    private Duration totalCheckInTime;

    private int totalAbsentDays;

    private int totalLateDays;

    private int totalEarlyBreakDays;

    private int totalApprovedAbsentDays;
}
