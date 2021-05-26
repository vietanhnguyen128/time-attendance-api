package com.example.TimeAttendanceAPI.dto;

import com.example.TimeAttendanceAPI.model.Department;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class DepartmentDTO {
    private Integer departmentId;

    @NotBlank
    private String departmentName;

    private Integer managerId;

    private String managerName;

    public DepartmentDTO(Department department) {
        this.departmentId = department.getDepartmentId();
        this.departmentName = department.getDepartmentName();
        this.managerId = department.getManager() == null ? null : department.getManager().getUserId();
        this.managerName = department.getManager() == null ? "" : department.getManager().getName();
    }
}
