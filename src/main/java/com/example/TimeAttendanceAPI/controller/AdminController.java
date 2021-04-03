package com.example.TimeAttendanceAPI.controller;

import com.example.TimeAttendanceAPI.model.Role;
import com.example.TimeAttendanceAPI.model.Department;
import com.example.TimeAttendanceAPI.model.Employee;
import com.example.TimeAttendanceAPI.model.FormRecord;
import com.example.TimeAttendanceAPI.model.Position;
import com.example.TimeAttendanceAPI.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class AdminController {

    private final AdminService adminService;

    //Employee methods
    @Operation(summary = "Add new employee", description = "Add new employee")
    @ApiResponse(responseCode = "200", description = "New employee added.")
    @PostMapping("/employee/add")
    public ResponseEntity<Employee> createEmployee(@RequestBody @Valid Employee employee) {
        return new ResponseEntity<>(adminService.createNewEmployee(employee), HttpStatus.OK);
    }

    @Operation(summary = "Update employee", description = "Update employee info, based on employee id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee info successfully updated."),
            @ApiResponse(responseCode = "404", description = "Employee not found")}
    )
    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Integer id, @RequestBody @Valid Employee employee) throws IllegalAccessException {
        Employee result = adminService.updateEmployee(id, employee);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get all employee", description = "Get a list of all employee")
    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Employee.class)))})
    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(adminService.getAllEmployee(), HttpStatus.OK);
    }

    @Operation(summary = "Get employee", description = "Get employee info, based on employee id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success."),
            @ApiResponse(responseCode = "404", description = "Employee not found")}
    )
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Integer id) {
        Optional<Employee> result = adminService.getEmployeeById(id);
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Delete employee", description = "Delete employee info, based on employee id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Employee not found")}
    )
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable("id") Integer id) {
        Optional<Employee> result = adminService.getEmployeeById(id);
        if (result.isPresent()) {
            adminService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Department methods
    @Operation(summary = "Add new department", description = "Add new department")
    @ApiResponse(responseCode = "200", description = "New department added.")
    @PostMapping("/department/add")
    public ResponseEntity<Department> createDepartment(@RequestBody @Valid Department department) {
        return new ResponseEntity<>(adminService.createNewDepartment(department), HttpStatus.OK);
    }

    @Operation(summary = "Get departments", description = "Get a list of all departments")
    @ApiResponse(responseCode = "200", description = "Success", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Department.class)))})
    @GetMapping("/department")
    public ResponseEntity<List<Department>> getDepartments() {
        return new ResponseEntity<>(adminService.getAllDepartment(), HttpStatus.OK);
    }

    @Operation(summary = "Update department", description = "Update department info, based on department id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Department info successfully updated."),
            @ApiResponse(responseCode = "404", description = "Department not found")}
    )
    @PutMapping("/department")
    public ResponseEntity<Department> updateDepartment(@Param("id") Integer id, @RequestBody @Valid Department department) throws IllegalAccessException {
        Department result = adminService.updateDepartment(id, department);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Delete department", description = "Delete department info, based on department id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Department not found")}
    )
    @DeleteMapping("/department")
    public ResponseEntity<Department> deleteDepartment(@Param("id") Integer id) throws IllegalAccessException {
        if (adminService.deleteDepartment(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    //Position methods
    @Operation(summary = "Add new position", description = "Add new position")
    @ApiResponse(responseCode = "200", description = "New position added.")
    @PostMapping("/position/add")
    public ResponseEntity<Position> createPosition(@RequestBody @Valid Position position) {
        return new ResponseEntity<>(adminService.createNewPosition(position), HttpStatus.OK);
    }

    @Operation(summary = "Get positions", description = "Get a list of all positions")
    @ApiResponse(responseCode = "200", description = "Success", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Position.class)))})
    @GetMapping("/position")
    public ResponseEntity<List<Position>> getPositions() {
        return new ResponseEntity<>(adminService.getAllPosition(), HttpStatus.OK);
    }

    @Operation(summary = "Update position", description = "Update position info, based on position id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Position info successfully updated."),
            @ApiResponse(responseCode = "404", description = "Position not found")}
    )
    @PutMapping("/position")
    public ResponseEntity<Position> updatePosition(@Param("id") Integer id, @RequestBody @Valid Position position) throws IllegalAccessException {
        Position result = adminService.updatePosition(id, position);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Delete position", description = "Delete position info, based on position id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Position not found")}
    )
    @DeleteMapping("/position")
    public ResponseEntity<Position> deletePosition(@Param("id") Integer id) throws IllegalAccessException {
        if (adminService.deletePosition(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //AccountRole methods
    @Operation(summary = "Add new role", description = "Add new role")
    @ApiResponse(responseCode = "200", description = "Success")
    @PostMapping("/role/add")
    public ResponseEntity<Role> createAccountRole(@RequestBody @Valid Role role) {
        return new ResponseEntity<>(adminService.createNewRole(role), HttpStatus.OK);
    }

    @Operation(summary = "Get roles", description = "Get a list of all roles")
    @ApiResponse(responseCode = "200", description = "Success", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Role.class)))})
    @GetMapping("/role")
    public ResponseEntity<List<Role>> getAccountRoles() {
        return new ResponseEntity<>(adminService.getALlRole(), HttpStatus.OK);
    }

    @Operation(summary = "Update role", description = "Update role info, based on role id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Role not found")}
    )
    @PutMapping("/role")
    public ResponseEntity<Role> updateAccountRole(@Param("id") Integer id, @RequestBody @Valid Role role) throws IllegalAccessException {
        Role result = adminService.updateRole(id, role);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Delete role", description = "Delete role info, based on role id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Role not found")}
    )
    @DeleteMapping("/role")
    public ResponseEntity<Role> deleteAccountRole(@Param("id") Integer id) throws IllegalAccessException {
        if (adminService.deleteRole(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Form methods
    @Operation(summary = "Get form records", description = "Get all of subordinates form records")
    @ApiResponse(responseCode = "200", description = "Success", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = FormRecord.class)))} )
    @GetMapping("/form/{id}")
    public ResponseEntity<List<FormRecord>> getSubordinatesFormRecords(@PathVariable("id") Integer adminId) {
        return new ResponseEntity<>(adminService.getSubordinatesFormRecords(adminId), HttpStatus.OK);
    }

    @Operation(summary = "Get form records by type", description = "Get all of subordinates form records by type")
    @ApiResponse(responseCode = "200", description = "Success", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = FormRecord.class)))} )
    @GetMapping(value = "/form/{id}", params = "type")
    public ResponseEntity<List<FormRecord>> getSubordinatesFormRecordsByType(@PathVariable("id") Integer adminId, @RequestParam String type) {
        return new ResponseEntity<>(adminService.getSubordinatesFormRecordsByType(adminId, type), HttpStatus.OK);
    }

    @Operation(summary = "Get form records by status", description = "Get all of subordinates form records by status")
    @ApiResponse(responseCode = "200", description = "Success", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = FormRecord.class)))} )
    @GetMapping(value = "/form/{id}", params = "status")
    public ResponseEntity<List<FormRecord>> getSubordinatesFormRecordsByStatus(@PathVariable("id") Integer adminId, @RequestParam String status) {
        return new ResponseEntity<>(adminService.getSubordinatesFormRecordsByStatus(adminId, status), HttpStatus.OK);
    }

    @Operation(summary = "Form approval", description = "Approve a from record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = FormRecord.class))}),
            @ApiResponse(responseCode = "404", description = "Form ID not found")})
    @PutMapping(value = "/form/{id}", params = {"formId", "status"})
    public ResponseEntity<FormRecord> formApproval(@PathVariable("id") Integer adminId, @Param("formId") Integer formId, @Param("status") String status) {
        FormRecord result = adminService.formApproval(formId, status);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
