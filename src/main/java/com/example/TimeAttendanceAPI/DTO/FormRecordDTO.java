package com.example.TimeAttendanceAPI.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class FormRecordDTO {
    private Integer id;
    private Integer employeeId;
    private LocalDate date;
    private LocalTime timePeriod;
    private String formType;
    private String status;
}
