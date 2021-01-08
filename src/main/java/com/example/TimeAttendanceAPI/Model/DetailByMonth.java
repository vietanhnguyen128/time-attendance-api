package com.example.TimeAttendanceAPI.Model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalTime;
import java.time.Month;

@Entity
@Table(name = "Detail_By_Month")
public class DetailByMonth {

    @Id
    private int detailId;

    @NotNull
    @Column(name = "employee_id")
    @Getter @Setter
    private int employeeId;

    @NotNull
    @Column(name = "month")
    @Min(1) @Max(12)
    @Getter @Setter
    private Integer month;

    @NotNull
    @Column(name = "days_off")
    @Min(value = 0, message = "Days off must be at least 0")
    @Getter @Setter
    private Integer daysOff;

    @NotNull
    @Column(name = "hours_ot")
    @Min(value = 0, message = "Days OT must be at least 0")
    @Getter @Setter
    private Integer hoursOT;

    @NotNull
    @Column(name = "start_time")
    @Getter @Setter
    private Integer startTime;

    @NotNull
    @Column(name = "end_time")
    @Getter @Setter
    private Integer endTime;

    @NotNull
    @Column(name = "break_time")
    @Getter @Setter
    private Integer breakTime;
}
