package com.example.TimeAttendanceAPI.model;

import com.example.TimeAttendanceAPI.dto.FormRecordDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class FormRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private int managerId;

    private String formType;

    @Column(columnDefinition = "DATE")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @Column(columnDefinition = "TIME")
    @JsonFormat(pattern = "HH:mm:ss")
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime startTime;

    @Column(columnDefinition = "TIME")
    @JsonFormat(pattern = "HH:mm:ss")
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime endTime;

//    @Column(columnDefinition = "TIME")
//    @JsonFormat(pattern = "HH:mm:ss")
//    @JsonSerialize(using = LocalTimeSerializer.class)
//    private LocalTime timePeriod;

    private String status;

    public FormRecord(FormRecordDTO record) {
        this.formType = record.getFormType();
        this.date = record.getDate();
        this.startTime = record.getStartTime();
        this.endTime = record.getEndTime();
        this.status = record.getStatus();
    }
}
