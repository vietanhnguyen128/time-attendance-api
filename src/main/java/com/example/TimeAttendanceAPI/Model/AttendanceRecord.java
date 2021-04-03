package com.example.TimeAttendanceAPI.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;

    @Column(columnDefinition = "DATE")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateRecord;

    @Column(columnDefinition = "TIME")
    @JsonFormat(pattern = "HH:mm:ss")
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime timeRecord;

    private String type;
}
