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
    abstract Employee createNewEmployee(Employee employee);
    abstract Department createNewDepartment(Department department);
    abstract Position createNewPosition(Position position);
    abstract AccountRole createNewRole(AccountRole role);

    //Read
    abstract ArrayList<Employee> getAllEmployee();
    abstract Optional<Employee> getEmployeeById(Integer employeeId);
    abstract ArrayList<Department> getAllDepartment();
    abstract ArrayList<Position> getAllPosition();
    abstract ArrayList<AccountRole> getALlRole();

    //Update
    abstract Employee updateEmployee(Integer id, Employee employee) throws IllegalAccessException;
    abstract Department updateDepartment(Integer id, Department department) throws IllegalAccessException;
    abstract Position updatePosition(Integer id, Position position) throws IllegalAccessException;
    abstract AccountRole updateRole(Integer id, AccountRole role) throws IllegalAccessException;

    //Delete
    abstract boolean deleteEmployee(Integer id);
    abstract boolean deleteDepartment(Integer id);
    abstract boolean deletePosition(Integer id);
    abstract boolean deleteRole(Integer id);

    //Form
    abstract ArrayList<FormRecord> getSubordinatesFormRecords(Integer adminId);
    abstract ArrayList<FormRecord> getSubordinatesFormRecordsByType(Integer adminId, String type);
    abstract ArrayList<FormRecord> getSubordinatesFormRecordsByStatus(Integer adminId, String status);
    abstract FormRecord formApproval(Integer formId, String status);
}
