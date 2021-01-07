package com.example.TimeAttendanceAPI.Model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalTime;
import java.time.Month;

@Entity
@Table(name = "Detail_By_Month")
public class DetailByMonth {

    @NotNull
    @Column(name = "employee_id")
    @Getter @Setter
    private int employeeId;

    @NotNull
    @Column(name = "month")
    @Getter @Setter
    private Month month;

    @NotNull
    @Column(name = "days_off")
    @Min(value = 0, message = "Days off must be at least 0")
    @Getter @Setter
    private Integer daysOff;

    @NotNull
    @Column(name = "hours_ot")
    @Min(value = 0, message = "Days OT must be at least 0")
    @Getter @Setter
    private LocalTime hoursOT;
}
