package com.example.TimeAttendanceAPI.Model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "id")
    @Min(0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;

    @Column(name = "employee_name")
    @Size(min = 6, max = 100)
    private String name;

    @Column(name = "age")
    @Min(value = 18, message = "Age must be over 18")
    @Max(value = 60, message = "Age must not be over 60")
    private Integer age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "position_id")
    private Integer positionId;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "manager_id")
    private Integer managerId;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time", columnDefinition = "TIME")
    private LocalTime endTime;

    @Column(name = "break_time", columnDefinition = "TIME")
    private LocalTime breakTime;

    @Column(name = "total_leave_time", columnDefinition = "INT default 0")
    private Duration totalLeaveTime;

    @Column(name = "total_overtime", columnDefinition = "INT default 0")
    private Duration totalOvertime;

    @Column(name = "username")
    @Size(min = 5, max = 50)
    private String username;

    @Column(name = "password")
    @Size(min = 7, max = 100)
    private String password;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
}
