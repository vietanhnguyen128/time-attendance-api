package com.example.TimeAttendanceAPI.Controller;

import com.example.TimeAttendanceAPI.Model.DetailByMonth;
import com.example.TimeAttendanceAPI.Model.Employee;
import com.example.TimeAttendanceAPI.Service.AdminServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    @PostMapping("/employee/add")
    public ResponseEntity<Employee> createEmployee(@RequestBody @Valid Employee employee) {
        adminService.createEmployee(employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Integer id, @RequestBody @Valid Employee employee) {
        Employee result = adminService.updateEmployeeInfo(id, employee);

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> result = adminService.findAllEmployee();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Integer id) {
        Optional<Employee> result = adminService.findEmployeeById(id);

        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable("id") Integer id) {
        if (adminService.deleteEmployee(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/detail/{id}/{month}")
    public ResponseEntity<DetailByMonth> createDetailByMonth(@PathVariable("id") Integer employeeId, @PathVariable("mID") Month month, @RequestBody @Valid DetailByMonth detail) {
        adminService.createDetailByMonth(detail);
        return new ResponseEntity<>(detail, HttpStatus.OK);
    }

    @PutMapping("/detail/{id}/{month}")
    public ResponseEntity<DetailByMonth> updateDetailByMonth(@PathVariable("id") Integer employeeId, @PathVariable("mID") Month month, @RequestBody @Valid DetailByMonth detail) {
        DetailByMonth result = adminService.updateDetailByMonth(detail);

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
