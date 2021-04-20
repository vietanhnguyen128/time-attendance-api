package com.example.TimeAttendanceAPI.service.department;
import com.example.TimeAttendanceAPI.dto.DepartmentDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);

    Page<DepartmentDTO> getDepartmentList(int pageNo, int pageSize, String sortBy);

    DepartmentDTO getSingleDepartment(Integer departmentId);

    DepartmentDTO updateDepartment(DepartmentDTO departmentDTO);

    void deleteDepartment(Integer departmentId);
}
