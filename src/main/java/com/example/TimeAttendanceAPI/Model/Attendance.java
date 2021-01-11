package com.example.TimeAttendanceAPI.Model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "attendance")
public class Attendance {

    @Id
    @Column(name = "id")
    @Min(0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "employee_id")
    private Integer employee_id;

    @Column(name = "date_record", columnDefinition = "DATE")
    private LocalDate dateRecord;

    @Column(name = "time_record", columnDefinition = "TIME")
    private LocalTime timeRecord;

    @Column(name = "created_by")
    @Min(0)
    private Integer createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
