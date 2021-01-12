package com.example.TimeAttendanceAPI.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "id")
    @Min(0)
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @Min(0)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "position_id")
    @Min(0)
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "role_id")
    @Min(0)
    private AccountRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "employeeId")
    @Min(0)
    private Employee manager;

    @Column(name = "start_time", columnDefinition = "TIME")
    private LocalTime startTime;

    @Column(name = "end_time", columnDefinition = "TIME")
    private LocalTime endTime;

    @Column(name = "break_time", columnDefinition = "TIME")
    private LocalTime breakTime;

    @Column(name = "total_leave_time", columnDefinition = "TIME")
    private LocalTime totalLeaveTime;

    @Column(name = "total_overtime", columnDefinition = "TIME")
    private LocalTime totalOvertime;

    @Column(name = "username")
    @Size(min = 5, max = 50)
    private String username;

    @Column(name = "password")
    @Size(min = 7, max = 100)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "created_by")
    @Min(0)
    private Employee createdBy;

    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yy HH:mm:ss")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "updated_by")
    @Min(0)
    private Employee updatedBy;

    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "manager")
    private List<Employee> subordinates = new ArrayList<>();
}
