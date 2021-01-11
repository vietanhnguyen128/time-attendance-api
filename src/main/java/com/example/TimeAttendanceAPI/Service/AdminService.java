package com.example.TimeAttendanceAPI.Service;

import com.example.TimeAttendanceAPI.Model.Department;
import com.example.TimeAttendanceAPI.Model.Employee;
import com.example.TimeAttendanceAPI.Model.Position;
import com.example.TimeAttendanceAPI.Model.AccountRole;

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
    Department updateDepartment(Integer id, Department department);
    Position updatePosition(Integer id, Position position);
    AccountRole updateRole(Integer id, AccountRole role);

    //Delete
    boolean deleteEmployee(Integer id);
    boolean deleteDepartment(Integer id);
    boolean deletePosition(Integer id);
    boolean deleteRole(Integer id);
}
