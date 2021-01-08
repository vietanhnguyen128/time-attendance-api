package com.example.TimeAttendanceAPI.Service;

import com.example.TimeAttendanceAPI.Model.DetailByMonth;
import com.example.TimeAttendanceAPI.Model.Employee;

import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface AdminService {

    Employee createEmployee(Employee employee);
    Employee updateEmployeeInfo(Integer id, Employee employee);
    boolean deleteEmployee(Integer id);
    List<Employee> findAllEmployee();
    Optional<Employee> findEmployeeById(Integer id);
    DetailByMonth createDetailByMonth(DetailByMonth detail);
    DetailByMonth updateDetailByMonth(DetailByMonth detail);
}
