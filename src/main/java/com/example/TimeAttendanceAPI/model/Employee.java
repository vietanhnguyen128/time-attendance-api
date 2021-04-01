package com.example.TimeAttendanceAPI.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Duration;

@Entity
@Data
@NoArgsConstructor
@Table
public class Employee extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;

    private String name;

    @Min(value = 18, message = "Age must be over 18")
    @Max(value = 60, message = "Age must not be over 60")
    private Integer age;

    private String gender;

    private Integer departmentId;

    private Integer positionId;

    private Integer roleId;

    private Integer managerId;

    @Column(columnDefinition = "INT default 0")
    private Duration totalLeaveTime;

    @Column(columnDefinition = "INT default 0")
    private Duration totalOvertime;

    private String username;

    private String password;

    private String token;
}
