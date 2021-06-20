package com.example.TimeAttendanceAPI.controller.department;

import com.example.TimeAttendanceAPI.dto.DepartmentDTO;
import com.example.TimeAttendanceAPI.dto.PagedResponse;
import com.example.TimeAttendanceAPI.model.Department;
import com.example.TimeAttendanceAPI.service.department.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping("/department")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO request) {
        return new ResponseEntity<>(departmentService.createDepartment(request), HttpStatus.OK);
    }

    @GetMapping("/department")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(
            responseCode = "200",
            content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DepartmentDTO.class)))}
    )
    public ResponseEntity<PagedResponse> getDepartmentList(@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                                           @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
                                                           @RequestParam(name = "sortBy", defaultValue = "+departmentId") String sortBy) {
        return new ResponseEntity<>(departmentService.getDepartmentList(pageNo, pageSize, sortBy), HttpStatus.OK);
    }

    @GetMapping("/department/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable("id") Integer departmentId) {
        return new ResponseEntity<>(departmentService.getSingleDepartment(departmentId), HttpStatus.OK);
    }

    @PutMapping("/department/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<DepartmentDTO> updateDepartmentInfo(@PathVariable("id") Integer departmentId, @RequestBody @Valid DepartmentDTO departmentInfo) {
        return new ResponseEntity<>(departmentService.updateDepartment(departmentInfo), HttpStatus.OK);
    }

    @DeleteMapping("/department/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(
            responseCode = "204",
            description = "Delete success"
    )
    public ResponseEntity<?> deleteDepartment(@PathVariable("id") Integer departmentId) {
        departmentService.deleteDepartment(departmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
