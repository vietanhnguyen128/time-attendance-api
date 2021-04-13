package com.example.TimeAttendanceAPI.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class FormRecordDTO {
    private Integer id;

    private Integer employeeId;

    private Integer managerId;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String formType;

    private String status;
}