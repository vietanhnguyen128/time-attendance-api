package com.example.TimeAttendanceAPI.model;

import com.example.TimeAttendanceAPI.dto.DepartmentDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer departmentId;

    private String departmentName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<User> employeesInDepartment;

    public Department(DepartmentDTO departmentDTO) {
        this.departmentId = departmentDTO.getDepartmentId();
        this.departmentName = departmentDTO.getDepartmentName();
    }
}
