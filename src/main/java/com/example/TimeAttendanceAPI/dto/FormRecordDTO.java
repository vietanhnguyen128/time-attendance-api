package com.example.TimeAttendanceAPI.dto;

import com.example.TimeAttendanceAPI.model.FormRecord;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class FormRecordDTO {
    private Integer id;

    private Integer userId;

    private String managerName;

    private String formType;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String status;

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