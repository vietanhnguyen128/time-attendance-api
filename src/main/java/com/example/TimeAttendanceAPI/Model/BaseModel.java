package com.example.TimeAttendanceAPI.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class BaseModel {
    @Column
    protected Integer createdBy;

    @Column
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    protected LocalDateTime createdAt;

    @Column
    protected Integer updatedBy;

    @Column
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    protected LocalDateTime updatedAt;

    protected boolean deleted = false;
}
