package com.example.TimeAttendanceAPI.dto;

import com.example.TimeAttendanceAPI.model.AttendanceRecord;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceRecordDTO {
    private int userId;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm:ss")
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime checkIn;

    @JsonFormat(pattern = "HH:mm:ss")
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime checkOut;

    public AttendanceRecordDTO(AttendanceRecord record) {
        this.userId = record.getUser().getUserId();
        this.date = record.getDate();
        this.checkIn = record.getCheckIn();
        this.checkOut = record.getCheckOut();
    }
}
