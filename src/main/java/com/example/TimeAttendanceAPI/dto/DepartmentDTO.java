package com.example.TimeAttendanceAPI.dto;

import com.example.TimeAttendanceAPI.model.Department;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentDTO {
    private Integer departmentId;

    private String departmentName;

    private Integer managerId;

    private String managerName;

    public DepartmentDTO(Department department) {
        this.departmentId = department.getDepartmentId();
        this.departmentName = department.getDepartmentName();
        this.managerId = department.getManager().getUserId();
        this.managerName = department.getDepartmentName();
    }
}
