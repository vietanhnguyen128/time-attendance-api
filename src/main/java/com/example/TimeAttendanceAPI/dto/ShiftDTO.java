package com.example.TimeAttendanceAPI.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class ShiftDTO {
    private int shiftId;

    private String shiftName;

    private LocalTime startTime;

    private LocalTime endTime;
}
