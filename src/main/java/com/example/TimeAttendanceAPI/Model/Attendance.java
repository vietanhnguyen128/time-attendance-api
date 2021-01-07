package com.example.TimeAttendanceAPI.Model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "Attendance")
public class Attendance {

    @Id
    @Column(name = "employee_id")
    @Getter
    private Integer employeeId;

    @NotNull
    @PastOrPresent
    @Column(name = "date_of_work")
    @Getter @Setter
    private LocalDate dateOfWork;

    @NotNull
    @Column(name = "start_time")
    @Getter @Setter
    private LocalTime startTime;

    @NotNull
    @Column(name = "end_time")
    @Getter @Setter
    private LocalTime endTime;
}
