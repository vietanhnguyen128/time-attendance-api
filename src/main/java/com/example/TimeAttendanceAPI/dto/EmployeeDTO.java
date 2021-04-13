package com.example.TimeAttendanceAPI.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class EmployeeDTO {
    private Integer employeeId;

    private String name;

    private Integer age;

    private String gender;

    private Integer departmentId;

    private Integer positionId;

    private Integer roleId;

    private Integer managerId;

    private LocalTime shiftStart;

    private LocalTime shiftEnd;

    private LocalTime breakStart;

    private LocalTime breakEnd;
}
