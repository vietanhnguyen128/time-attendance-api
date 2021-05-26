package com.example.TimeAttendanceAPI.dto;

import com.example.TimeAttendanceAPI.model.FormRecord;
import com.example.TimeAttendanceAPI.model._enum.FormStatus;
import com.example.TimeAttendanceAPI.model._enum.FormType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class FormRecordDTO {
    private Integer id;

    @NotBlank
    private Integer userId;

    private Integer managerId;

    private String managerName;

    @NotBlank
    private FormType formType;

    @NotBlank
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @NotBlank
    @JsonFormat(pattern = "HH:mm:ss")
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime startTime; //todo refactor fields

    @NotBlank
    @JsonFormat(pattern = "HH:mm:ss")
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime endTime;

    private FormStatus status  = FormStatus.PENDING;

    public FormRecordDTO(FormRecord formRecord) {
        this.id = formRecord.getId();
        this.userId = formRecord.getUser().getUserId();
        this.managerId = formRecord.getManager() == null ? null : formRecord.getManager().getUserId();
        this.managerName = formRecord.getManager() == null ? "" : formRecord.getManager().getName();
        this.formType = formRecord.getFormType();
        this.date = formRecord.getDate();
        this.startTime = formRecord.getStartTime();
        this.endTime = formRecord.getEndTime();
        this.status = formRecord.getStatus();
    }
}