package com.example.TimeAttendanceAPI.domain.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attendance")
public class Attendance {

    @Id
    @Column(name = "id")
    @Min(0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "date_record", columnDefinition = "DATE")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateRecord;

    @Column(name = "time_record", columnDefinition = "TIME")
    @JsonFormat(pattern = "HH:mm:ss")
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime timeRecord;
}
