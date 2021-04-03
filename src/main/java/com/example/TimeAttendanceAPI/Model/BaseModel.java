package com.example.TimeAttendanceAPI.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class BaseModel {
    @Column
    private Integer createdBy;

    @Column
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @Column
    private Integer updatedBy;

    @Column
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;

    private boolean deleted = false;
}
