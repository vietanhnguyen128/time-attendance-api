package com.example.TimeAttendanceAPI.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(0)
    private Integer id;

    @JoinColumn(name = "employee_id")
    private Integer employeeId;

    @Column(name = "day", columnDefinition = "DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;

    @Column(name = "time_period", columnDefinition = "TIME")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime timePeriod;

    @Column(name = "form_type")
    @Size(min = 0)
    private String formType;

    @Column(name = "status")
    @Size(min = 0)
    private String status;

    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @JoinColumn(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
}
