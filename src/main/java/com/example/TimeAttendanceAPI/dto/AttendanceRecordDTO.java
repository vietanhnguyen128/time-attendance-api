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
    private int userId;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm:ss")
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime timestamp;

    @JsonProperty
    private boolean isCheckIn;

    public AttendanceRecordDTO(AttendanceRecord record) {
        this.userId = record.getUser().getUserId();
        this.date = record.getDate();
        this.timestamp = record.getTimestamp();
        this.isCheckIn = record.isCheckIn();
    }
}
