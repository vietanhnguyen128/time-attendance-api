package com.example.TimeAttendanceAPI.Model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "form_record")
public class FormRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Min(0)
    private Integer id;

    @Column(name = "employee_id")
    @Min(0)
    private Integer employeeId;

    @Column(name = "day", columnDefinition = "DATE")
    private LocalDate date;

    @Column(name = "time_period", columnDefinition = "TIME")
    private LocalTime timePeriod;

    @Column(name = "form_type")
    @Size(min = 0)
    private String formType;

    @Column(name = "status")
    @Size(min = 0)
    private String status;

    @Column(name = "created_by")
    @Min(0)
    private Integer createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    @Min(0)
    private Integer updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
