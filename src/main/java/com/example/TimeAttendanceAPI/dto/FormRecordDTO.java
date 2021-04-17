package com.example.TimeAttendanceAPI.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class FormRecordDTO {
    private Integer id;

    private Integer userId;

    private Integer managerId;

    private String formType;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String status;
}