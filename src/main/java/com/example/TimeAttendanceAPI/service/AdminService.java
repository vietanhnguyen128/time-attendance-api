package com.example.TimeAttendanceAPI.service;


import com.example.TimeAttendanceAPI.model.AccountRole;
import com.example.TimeAttendanceAPI.model.Department;
import com.example.TimeAttendanceAPI.model.Employee;
import com.example.TimeAttendanceAPI.model.FormRecord;
import com.example.TimeAttendanceAPI.model.Position;

import java.util.ArrayList;
import java.util.Optional;

public interface AdminService {

    //Create
    Employee createNewEmployee(Employee employee);
    Department createNewDepartment(Department department);
    Position createNewPosition(Position position);
    AccountRole createNewRole(AccountRole role);

    //Read
    ArrayList<Employee> getAllEmployee();
    Optional<Employee> getEmployeeById(Integer employeeId);
    ArrayList<Department> getAllDepartment();
    ArrayList<Position> getAllPosition();
    ArrayList<AccountRole> getALlRole();

    //Update
    Employee updateEmployee(Integer id, Employee employee) throws IllegalAccessException;
    Department updateDepartment(Integer id, Department department) throws IllegalAccessException;
    Position updatePosition(Integer id, Position position) throws IllegalAccessException;
    AccountRole updateRole(Integer id, AccountRole role) throws IllegalAccessException;

    //Delete
    boolean deleteEmployee(Integer id);
    boolean deleteDepartment(Integer id);
    boolean deletePosition(Integer id);
    boolean deleteRole(Integer id);

    //Form
    ArrayList<FormRecord> getSubordinatesFormRecords(Integer adminId);
    ArrayList<FormRecord> getSubordinatesFormRecordsByType(Integer adminId, String type);
    ArrayList<FormRecord> getSubordinatesFormRecordsByStatus(Integer adminId, String status);
    FormRecord formApproval(Integer formId, String status);
}
