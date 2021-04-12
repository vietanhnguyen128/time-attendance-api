package com.example.TimeAttendanceAPI.model;

import com.example.TimeAttendanceAPI.dto.DepartmentDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Department extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentId;

    private String departmentName;

    private String managerId;

    public Department(DepartmentDTO departmentDTO) {
        this.departmentId = departmentDTO.getDepartmentId();
        this.departmentName = departmentDTO.getDepartmentName();
        this.managerId = departmentDTO.getManagerId();
    }
}
