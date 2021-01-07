package com.example.TimeAttendanceAPI.Repository;

import com.example.TimeAttendanceAPI.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
