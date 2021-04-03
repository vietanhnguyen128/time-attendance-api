package com.example.TimeAttendanceAPI.service.impl;

import com.example.TimeAttendanceAPI.model.AccountRole;
import com.example.TimeAttendanceAPI.model.Department;
import com.example.TimeAttendanceAPI.model.Employee;
import com.example.TimeAttendanceAPI.model.FormRecord;
import com.example.TimeAttendanceAPI.model.Position;
import com.example.TimeAttendanceAPI.repository.AccountRoleRepository;
import com.example.TimeAttendanceAPI.repository.DepartmentRepository;
import com.example.TimeAttendanceAPI.repository.EmployeeRepository;
import com.example.TimeAttendanceAPI.repository.FormRecordRepository;
import com.example.TimeAttendanceAPI.repository.PositionRepository;
import com.example.TimeAttendanceAPI.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final AccountRoleRepository accountRoleRepository;
    private final FormRecordRepository formRecordRepository;

    //Create
    @Override
    public Employee createNewEmployee(Employee employee) {
        if (employee.getTotalLeaveTime() == null) {
            employee.setTotalLeaveTime(Duration.ofSeconds(0));
        }
        if (employee.getTotalOvertime() == null) {
            employee.setTotalOvertime(Duration.ofSeconds(0));
        }
        if (employee.getCreatedAt() == null) {
            employee.setCreatedAt(LocalDateTime.now());
        }
        employee.setPassword(new BCryptPasswordEncoder().encode(employee.getPassword()));
        return employeeRepository.save(employee);
    }

    @Override
    public Department createNewDepartment(Department department) {
        if (department.getCreatedAt() == null) {
            department.setCreatedAt(LocalDateTime.now());
        }
        return departmentRepository.save(department);
    }

    @Override
    public Position createNewPosition(Position position) {
        if (position.getCreatedAt() == null) {
            position.setCreatedAt(LocalDateTime.now());
        }
        return positionRepository.save(position);
    }

    @Override
    public AccountRole createNewRole(AccountRole role) {
        if (role.getCreatedAt() == null) {
            role.setCreatedAt(LocalDateTime.now());
        }
        return accountRoleRepository.save(role);
    }



    //Read
    @Override
    public List<Employee> getAllEmployee() {
        List<Employee> results = employeeRepository.findAll();
        for (Employee e : results) {
            e.setUsername(null);
            e.setPassword(null);
        }
        return results;
    }

    @Override
    public Optional<Employee> getEmployeeById(Integer employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }

    @Override
    public List<Position> getAllPosition() {
        return positionRepository.findAll();
    }

    @Override
    public List<AccountRole> getALlRole() {
        return accountRoleRepository.findAll();
    }



    //Update
    @Override
    public Employee updateEmployee(Integer id, Employee employee) throws IllegalAccessException {
        Optional<Employee> currentVal = employeeRepository.findById(id);
        if(currentVal.isPresent()) {
            Employee newVal = currentVal.get();

            if (employee.getPassword() != null)
                employee.setPassword(new BCryptPasswordEncoder().encode(employee.getPassword()));

            Field[] fields = newVal.getClass().getDeclaredFields(); //Get an array of all fields including private field in object

            for (Field field : fields) { //for each field
                field.setAccessible(true); //set accessible for field, else throw IllegalAccessException
                Object value = field.get(employee); //get value of the field in employee object

                if (value != null) {
                    field.set(newVal, value); //set value of the field in object newVal with value
                }
            }

            if (newVal.getUpdatedAt() == null) {
                newVal.setUpdatedAt(LocalDateTime.now());
            }

            return employeeRepository.save(newVal);
        }

        return null;
    }

    @Override
    public Department updateDepartment(Integer id, Department department) throws IllegalAccessException {
        Optional<Department> currentVal = departmentRepository.findById(id);
        if(currentVal.isPresent()) {
            Department newVal = currentVal.get();

            Field[] fields = newVal.getClass().getDeclaredFields(); //Get an array of all fields including private field in object

            for (Field field : fields) { //for each
                field.setAccessible(true); //set accessible for field, else throw IllegalAccessException
                Object value = field.get(department); //get value of the field in department object

                if (value != null) {
                    field.set(newVal, value); //set value of the field in object newVal with v
                }
            }

            if (newVal.getUpdatedAt() == null)
                newVal.setUpdatedAt(LocalDateTime.now());

            return departmentRepository.save(newVal);
        }

        return null;
    }

    @Override
    public Position updatePosition(Integer id, Position position) throws IllegalAccessException {
        Optional<Position> currentVal = positionRepository.findById(id);
        if(currentVal.isPresent()) {
            Position newVal = currentVal.get();

            Field[] fields = newVal.getClass().getDeclaredFields(); //Get an array of all fields including private field in object

            for (Field field : fields) { //for each
                field.setAccessible(true); //set accessible for field, else throw IllegalAccessException
                Object v = field.get(position); //get value of the field in position object

                if (v != null) {
                    field.set(newVal, v); //set value of the field in object newVal with v
                }
            }

            if (newVal.getUpdatedAt() == null)
                newVal.setUpdatedAt(LocalDateTime.now());

            return positionRepository.save(newVal);
        }

        return null;
    }

    @Override
    public AccountRole updateRole(Integer id, AccountRole role) throws IllegalAccessException {
        Optional<AccountRole> currentVal = accountRoleRepository.findById(id);
        if(currentVal.isPresent()) {
            AccountRole newVal = currentVal.get();

            Field[] fields = newVal.getClass().getDeclaredFields(); //Get an array of all fields including private field in object

            for (Field field : fields) { //for each
                field.setAccessible(true); //set accessible for field, else throw IllegalAccessException
                Object v = field.get(role); //get value of the field in role object

                if (v != null) {
                    field.set(newVal, v); //set value of the field in object newVal with v
                }
            }

            if (newVal.getUpdatedAt() == null)
                newVal.setUpdatedAt(LocalDateTime.now());

            return accountRoleRepository.save(newVal);
        }

        return null;
    }



    //Delete
    @Override
    public boolean deleteEmployee(Integer id) {
        Optional<Employee> result = employeeRepository.findById(id);
        if (result.isPresent()) {
            employeeRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteDepartment(Integer id) {
        Optional<Department> result = departmentRepository.findById(id);
        if (result.isPresent()) {
            departmentRepository.deleteById(id);
            return true;
        }
        
        return false;
    }

    @Override
    public boolean deletePosition(Integer id) {
        Optional<Position> result = positionRepository.findById(id);
        if (result.isPresent()) {
            positionRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteRole(Integer id) {
        Optional<AccountRole> result = accountRoleRepository.findById(id);
        if (result.isPresent()) {
            accountRoleRepository.deleteById(id);
            return true;
        }

        return false;
    }


    //Handling forms
    @Override
    public List<FormRecord> getSubordinatesFormRecords(Integer adminId) {
        return formRecordRepository.getManagedFormRecords(adminId);
    }

    @Override
    public List<FormRecord> getSubordinatesFormRecordsByType(Integer adminId, String type) {
        List<FormRecord> result = getSubordinatesFormRecords(adminId);
        result.removeIf(form -> (!form.getFormType().equals(type)));
        return result;
    }

    @Override
    public List<FormRecord> getSubordinatesFormRecordsByStatus(Integer adminId, String status) {
        List<FormRecord> result = getSubordinatesFormRecords(adminId);
        result.removeIf(form -> (!form.getStatus().equals(status)));
        return result;
    }

    @Override
    public FormRecord formApproval(Integer formId, String status) {
        Optional<FormRecord> result = formRecordRepository.findById(formId);
        if (result.isPresent()) {
            FormRecord newRecord = result.get();
            newRecord.setStatus(status);
            return formRecordRepository.save(newRecord);
        }
        return null;
    }
}
