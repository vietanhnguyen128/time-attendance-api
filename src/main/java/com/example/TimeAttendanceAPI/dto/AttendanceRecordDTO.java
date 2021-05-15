package com.example.TimeAttendanceAPI.dto;

import com.example.TimeAttendanceAPI.model.AttendanceRecord;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRecordDTO {
    private Long id;

    private int userId;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm:ss")
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime checkInTimestamp;

    @JsonFormat(pattern = "HH:mm:ss")
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime checkOutTimestamp;

    public AttendanceRecordDTO(AttendanceRecord record) {
        this.id = record.getId();
        this.userId = record.getUser().getUserId();
        this.date = record.getDate();
        this.checkInTimestamp = record.getCheckInTimestamp();
        this.checkOutTimestamp = record.getCheckOutTimestamp();
    }
}
