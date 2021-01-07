package com.example.TimeAttendanceAPI.Service;

import com.example.TimeAttendanceAPI.Model.DetailByMonth;
import com.example.TimeAttendanceAPI.Model.Employee;

import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface AdminService {

    Employee createEmployee(Employee employee);
    List<Employee> getEmployees();
    Employee updateEmployeeInfo(Employee employee);
    boolean deleteEmployee(Integer id);
    DetailByMonth updateDetailByMonth(DetailByMonth detail);
    List<Employee> findAllEmployee();
    Optional<Employee> findEmployeeById(Integer id);
}
