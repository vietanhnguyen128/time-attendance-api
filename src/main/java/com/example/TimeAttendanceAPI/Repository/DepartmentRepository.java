package com.example.TimeAttendanceAPI.Repository;

import com.example.TimeAttendanceAPI.Model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
