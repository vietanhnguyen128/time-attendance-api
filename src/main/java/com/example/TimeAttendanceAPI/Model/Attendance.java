package com.example.TimeAttendanceAPI.Model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "Attendance")
public class Attendance {

    @Id
    private Integer id;

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
    @Min(0) @Max(24)
    private Integer startTime;

    @NotNull
    @Column(name = "end_time")
    @Min(0) @Max(24)
    @Getter @Setter
    private Integer endTime;
}
