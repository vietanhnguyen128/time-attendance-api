package com.example.TimeAttendanceAPI.Controller;

import com.example.TimeAttendanceAPI.Model.*;
import com.example.TimeAttendanceAPI.Service.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    //Employee
    @PostMapping("/employee/add")
    public ResponseEntity<Employee> createEmployee(@RequestBody @Valid Employee employee) {
        return new ResponseEntity<>(adminService.createNewEmployee(employee), HttpStatus.OK);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Integer id, @RequestBody @Valid Employee employee) throws IllegalAccessException {
        Employee result = adminService.updateEmployee(id, employee);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(adminService.getAllEmployee(), HttpStatus.OK);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Integer id) {
        Optional<Employee> result = adminService.getEmployeeById(id);
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable("id") Integer id) {
        Optional<Employee> result = adminService.getEmployeeById(id);
        if (result.isPresent()) {
            adminService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Department
    @PostMapping("/department/add")
    public ResponseEntity<Department> createDepartment(@RequestBody @Valid Department department) {
        return new ResponseEntity<>(adminService.createNewDepartment(department), HttpStatus.OK);
    }

    @GetMapping("/department")
    public ResponseEntity<List<Department>> getDepartments() {
        return new ResponseEntity<>(adminService.getAllDepartment(), HttpStatus.OK);
    }

    @PutMapping("/department")
    public ResponseEntity<Department> updateDepartment(@Param("id") Integer id, @RequestBody @Valid Department department) throws IllegalAccessException {
        Department result = adminService.updateDepartment(id, department);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/department")
    public ResponseEntity<Department> deleteDepartment(@Param("id") Integer id) throws IllegalAccessException {
        if (adminService.deleteDepartment(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    //Position
    @PostMapping("/position/add")
    public ResponseEntity<Position> createPosition(@RequestBody @Valid Position position) {
        return new ResponseEntity<>(adminService.createNewPosition(position), HttpStatus.OK);
    }

    @GetMapping("/position")
    public ResponseEntity<List<Position>> getPositions() {
        return new ResponseEntity<>(adminService.getAllPosition(), HttpStatus.OK);
    }

    @PutMapping("/position")
    public ResponseEntity<Position> updatePosition(@Param("id") Integer id, @RequestBody @Valid Position position) throws IllegalAccessException {
        Position result = adminService.updatePosition(id, position);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/position")
    public ResponseEntity<Position> deletePosition(@Param("id") Integer id) throws IllegalAccessException {
        if (adminService.deletePosition(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //AccountRole
    @PostMapping("/role/add")
    public ResponseEntity<AccountRole> createAccountRole(@RequestBody @Valid AccountRole role) {
        return new ResponseEntity<>(adminService.createNewRole(role), HttpStatus.OK);
    }

    @GetMapping("/role")
    public ResponseEntity<List<AccountRole>> getAccountRoles() {
        return new ResponseEntity<>(adminService.getALlRole(), HttpStatus.OK);
    }

    @PutMapping("/role")
    public ResponseEntity<AccountRole> updateAccountRole(@Param("id") Integer id, @RequestBody @Valid AccountRole role) throws IllegalAccessException {
        AccountRole result = adminService.updateRole(id, role);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/role")
    public ResponseEntity<AccountRole> deleteAccountRole(@Param("id") Integer id) throws IllegalAccessException {
        if (adminService.deleteRole(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Form
    @GetMapping("/form/{id}")
    public ResponseEntity<List<FormRecord>> getSubordinatesFormRecords(@PathVariable("id") Integer adminId) {
        return new ResponseEntity<>(adminService.getSubordinatesFormRecords(adminId), HttpStatus.OK);
    }

    @GetMapping("/form/{id}")
    public ResponseEntity<List<FormRecord>> getSubordinatesFormRecordsByType(@PathVariable("id") Integer adminId, @Param("type") String type) {
        return new ResponseEntity<>(adminService.getSubordinatesFormRecordsByType(adminId, type), HttpStatus.OK);
    }

    @GetMapping("/form/{id}")
    public ResponseEntity<List<FormRecord>> getSubordinatesFormRecordsByStatus(@PathVariable("id") Integer adminId, @Param("status") String status) {
        return new ResponseEntity<>(adminService.getSubordinatesFormRecordsByStatus(adminId, status), HttpStatus.OK);
    }

    @GetMapping("/form/{id}")
    public ResponseEntity<FormRecord> formApproval(@PathVariable("id") Integer adminId, @Param("id") Integer formId, @RequestBody String status) {
        FormRecord result = adminService.formApproval(formId, status);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
