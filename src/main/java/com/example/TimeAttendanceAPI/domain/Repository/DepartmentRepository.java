package com.example.TimeAttendanceAPI.domain.Repository;

import com.example.TimeAttendanceAPI.domain.Model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
