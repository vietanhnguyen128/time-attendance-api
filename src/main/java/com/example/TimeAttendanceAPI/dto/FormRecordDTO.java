package com.example.TimeAttendanceAPI.dto;

import com.example.TimeAttendanceAPI.model.FormRecord;
import com.example.TimeAttendanceAPI.model._enum.FormStatus;
import com.example.TimeAttendanceAPI.model._enum.FormType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class FormRecordDTO {
    private Integer id;

    @Valid
    private Integer userId;

    private String userName;

    private Integer managerId;

    private String managerName;

//    private FormType formType;

    @Schema(example = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

//    @JsonFormat(pattern = "HH:mm:ss")
//    @JsonSerialize(using = LocalTimeSerializer.class)
//    private LocalTime startTime; //todo refactor fields
//
//    @JsonFormat(pattern = "HH:mm:ss")
//    @JsonSerialize(using = LocalTimeSerializer.class)
//    private LocalTime endTime;

    private FormStatus status  = FormStatus.PENDING;

    public FormRecordDTO(FormRecord formRecord) {
        this.id = formRecord.getId();
        this.userId = formRecord.getUser().getUserId();
        this.userName = formRecord.getUser().getName();
        this.managerId = formRecord.getManager() == null ? null : formRecord.getManager().getUserId();
        this.managerName = formRecord.getManager() == null ? "" : formRecord.getManager().getName();
//        this.formType = formRecord.getFormType();
        this.date = formRecord.getDate();
//        this.startTime = formRecord.getStartTime();
//        this.endTime = formRecord.getEndTime();
        this.status = formRecord.getStatus();
    }
}