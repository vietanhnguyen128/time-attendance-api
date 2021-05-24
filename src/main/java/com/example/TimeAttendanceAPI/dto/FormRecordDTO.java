package com.example.TimeAttendanceAPI.dto;

import com.example.TimeAttendanceAPI.model.FormRecord;
import com.example.TimeAttendanceAPI.model._enum.FormStatus;
import com.example.TimeAttendanceAPI.model._enum.FormType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class FormRecordDTO {
    private Integer id;

    private Integer userId;

    private String managerName;

    private FormType formType;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    private LocalTime startTime; //todo refactor fields

    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private FormStatus status  = FormStatus.PENDING;

    public FormRecordDTO(FormRecord formRecord) {
        this.id = formRecord.getId();
        this.userId = formRecord.getUser().getUserId();
        this.managerName = formRecord.getManager().getName();
        this.formType = formRecord.getFormType();
        this.date = formRecord.getDate();
        this.startTime = formRecord.getStartTime();
        this.endTime = formRecord.getEndTime();
        this.status = formRecord.getStatus();
    }
}