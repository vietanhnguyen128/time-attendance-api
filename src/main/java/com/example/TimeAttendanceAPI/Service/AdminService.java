package com.example.TimeAttendanceAPI.Service;

import com.example.TimeAttendanceAPI.Model.*;

import java.util.List;

public interface AdminService {

    //Create
    Employee createNewEmployee(Employee employee);
    Department createNewDepartment(Department department);
    Position createNewPosition(Position position);
    AccountRole createNewRole(AccountRole role);

    //Read
    List<Employee> getAllEmployee();
    List<Department> getAllDepartment();
    List<Position> getAllPosition();
    List<AccountRole> getALlRole();

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
    List<FormRecord> getSubordinatesFormRecords();
    List<FormRecord> getSubordinatesFormRecordsByType(String type);
    List<FormRecord> getSubordinatesFormRecordsByStatus(String status);
    FormRecord formApproval(Integer id, String status);
}
