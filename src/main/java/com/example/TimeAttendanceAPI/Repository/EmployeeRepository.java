package com.example.TimeAttendanceAPI.Repository;

import com.example.TimeAttendanceAPI.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByUsername(String username);
    Employee findByToken(String token);
}
