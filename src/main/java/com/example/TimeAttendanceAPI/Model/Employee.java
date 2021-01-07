package com.example.TimeAttendanceAPI.Model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "Employee")
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    enum Role {
        ADMIN,
        USER
    }

    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private int employeeId;

    @NotNull
    @Column(name = "name")
    @Size(min = 6)
    @Getter @Setter
    private String name;

    @NotNull
    @Column(name = "age")
    @Min(value = 18, message = "Age must be over 18")
    @Max(value = 60, message = "Age must not be over 60")
    @Getter @Setter
    private Integer age;

    @NotNull
    @Column(name = "department")
    @Size(min = 6)
    @Getter @Setter
    private String department;

    @NotNull
    @Column(name = "position")
    @Size(min = 6)
    @Getter @Setter
    private String position;

    @NotNull
    @Column(name = "role")
    @Size(min = 1)
    @Getter @Setter
    private Role role;
}
