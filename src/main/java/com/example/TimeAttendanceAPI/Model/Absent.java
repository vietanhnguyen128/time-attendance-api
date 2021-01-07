package com.example.TimeAttendanceAPI.Model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Absent")
public class Absent {

    @Id
    @Column(name = "absent_id")
    @Getter
    private Integer absentId;

    @NotNull
    @Column(name = "employee_id")
    @Getter @Setter
    private Integer employeeId;

    @NotNull
    @Column(name = "date_of_absent")
    @Getter @Setter
    private LocalDate dateOfAbsent;

    @NotNull
    @Column(name = "absent_time")
    @Getter @Setter
    private Integer absentTime;
}
