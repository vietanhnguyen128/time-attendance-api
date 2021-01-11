package com.example.TimeAttendanceAPI.Service;

import com.example.TimeAttendanceAPI.Model.AccountRole;
import com.example.TimeAttendanceAPI.Model.Department;
import com.example.TimeAttendanceAPI.Model.Employee;
import com.example.TimeAttendanceAPI.Model.Position;
import com.example.TimeAttendanceAPI.Repository.AccountRoleRepository;
import com.example.TimeAttendanceAPI.Repository.DepartmentRepository;
import com.example.TimeAttendanceAPI.Repository.EmployeeRepository;
import com.example.TimeAttendanceAPI.Repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private AccountRoleRepository accountRoleRepository;

    //Create
    @Override
    public Employee createNewEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Department createNewDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Position createNewPosition(Position position) {
        return positionRepository.save(position);
    }

    @Override
    public AccountRole createNewRole(AccountRole role) {
        return accountRoleRepository.save(role);
    }



    //Read
    @Override
    public List<Employee> getAllEmployee() {
        List<Employee> result = employeeRepository.findAll();
        return result;
    }

    @Override
    public List<Department> getAllDepartment() {
        List<Department> result = departmentRepository.findAll();
        return result;
    }

    @Override
    public List<Position> getAllPosition() {
        List<Position> result = positionRepository.findAll();
        return result;
    }

    @Override
    public List<AccountRole> getALlRole() {
        List<AccountRole> result = accountRoleRepository.findAll();
        return result;
    }



    //Update
    @Override
    public Employee updateEmployee(Integer id, Employee employee) throws IllegalAccessException {
        Optional<Employee> currentVal = employeeRepository.findById(id);
        if(currentVal.isPresent()) {
            Employee newVal = currentVal.get();

            Field[] fields = newVal.getClass().getDeclaredFields();

            for (Field f : fields) {
                Class t = f.getType();
                Object v = f.get(employee);

                if (v != null) {
                    f.set(newVal, v);
                }
            }

            return employeeRepository.save(newVal);
        }

        return null;
    }

    @Override
    public Department updateDepartment(Integer id, Department department) {
        return null;
    }

    @Override
    public Position updatePosition(Integer id, Position position) {
        return null;
    }

    @Override
    public AccountRole updateRole(Integer id, AccountRole role) {
        return null;
    }



    //Delete
    @Override
    public boolean deleteEmployee(Integer id) {
        return false;
    }

    @Override
    public boolean deleteDepartment(Integer id) {
        return false;
    }

    @Override
    public boolean deletePosition(Integer id) {
        return false;
    }

    @Override
    public boolean deleteRole(Integer id) {
        return false;
    }
}
