package com.example.TimeAttendanceAPI.repository;

import com.example.TimeAttendanceAPI.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
