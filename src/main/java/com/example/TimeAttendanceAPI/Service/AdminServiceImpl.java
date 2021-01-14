package com.example.TimeAttendanceAPI.Service;

import com.example.TimeAttendanceAPI.Model.*;
import com.example.TimeAttendanceAPI.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private FormRecordRepository formRecordRepository;

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
    public ArrayList<Employee> getAllEmployee() {
        return (ArrayList<Employee>) employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Integer employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public ArrayList<Department> getAllDepartment() {
        return (ArrayList<Department>) departmentRepository.findAll();
    }

    @Override
    public ArrayList<Position> getAllPosition() {
        return (ArrayList<Position>) positionRepository.findAll();
    }

    @Override
    public ArrayList<AccountRole> getALlRole() {
        return (ArrayList<AccountRole>) accountRoleRepository.findAll();
    }



    //Update
    @Override
    public Employee updateEmployee(Integer id, Employee employee) throws IllegalAccessException {
        Optional<Employee> currentVal = employeeRepository.findById(id);
        if(currentVal.isPresent()) {
            Employee newVal = currentVal.get();

            Field[] fields = newVal.getClass().getDeclaredFields(); //Get an array of all fields including private field in object

            for (Field field : fields) { //for each
                field.setAccessible(true); //set accessible for field, else throw IllegalAccessException
                Class t = field.getType(); //get the type of the field
                Object v = field.get(employee); //get value of the field in employee object

                if (v != null) {
                    field.set(newVal, v); //set value of the field in object newVal with v
                }
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
                Class t = field.getType(); //get the type of the field
                Object v = field.get(department); //get value of the field in department object

                if (v != null) {
                    field.set(newVal, v); //set value of the field in object newVal with v
                }
            }

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
                Class t = field.getType(); //get the type of the field
                Object v = field.get(position); //get value of the field in position object

                if (v != null) {
                    field.set(newVal, v); //set value of the field in object newVal with v
                }
            }

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
                Class t = field.getType(); //get the type of the field
                Object v = field.get(role); //get value of the field in role object

                if (v != null) {
                    field.set(newVal, v); //set value of the field in object newVal with v
                }
            }

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
    public ArrayList<FormRecord> getSubordinatesFormRecords(Integer adminId) {
        return getSubordinatesFormRecords(adminId);
    }

    @Override
    public ArrayList<FormRecord> getSubordinatesFormRecordsByType(Integer adminId, String type) {
        ArrayList<FormRecord> result = getSubordinatesFormRecords(adminId);
        result.removeIf(form -> (form.getFormType() != type));
        return result;
    }

    @Override
    public ArrayList<FormRecord> getSubordinatesFormRecordsByStatus(Integer adminId, String status) {
        ArrayList<FormRecord> result = getSubordinatesFormRecords(adminId);
        result.removeIf(form -> (form.getStatus() != status));
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
