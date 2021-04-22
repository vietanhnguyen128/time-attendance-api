package com.example.TimeAttendanceAPI.controller.department;

import com.example.TimeAttendanceAPI.dto.DepartmentDTO;
import com.example.TimeAttendanceAPI.service.department.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping("/new")
    public ResponseEntity<DepartmentDTO> createDepartment(DepartmentDTO request) {
        return new ResponseEntity<>(departmentService.createDepartment(request), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentDTO>> getDepartmentList(@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                                                 @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
                                                                 @RequestParam(name = "sortBy") String sortBy) {
        return new ResponseEntity<>(departmentService.getDepartmentList(pageNo, pageSize, sortBy), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable("id") Integer departmentId) {
        return new ResponseEntity<>(departmentService.getSingleDepartment(departmentId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(Integer departmentId) {
        departmentService.deleteDepartment(departmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}