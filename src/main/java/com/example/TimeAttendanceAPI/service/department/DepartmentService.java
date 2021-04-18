package com.example.TimeAttendanceAPI.service.department;
import com.example.TimeAttendanceAPI.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);

    List<DepartmentDTO> getDepartmentList();

    DepartmentDTO getSingleDepartment(Integer departmentId);

    DepartmentDTO updateDepartment(DepartmentDTO departmentDTO);

    void deleteDepartment(Integer departmentId);
}
