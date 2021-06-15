package com.example.TimeAttendanceAPI.controller.form_record;

import com.example.TimeAttendanceAPI.dto.FormRecordDTO;
import com.example.TimeAttendanceAPI.dto.PagedResponse;
import com.example.TimeAttendanceAPI.service.form_record.FormRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import java.text.Normalizer;

@RestController
@RequiredArgsConstructor
public class FormRecordController {
    private final FormRecordService formRecordService;

    @PostMapping("/form")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_EMPLOYEE')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<FormRecordDTO> createForm(@RequestBody @Valid FormRecordDTO request) {
        return new ResponseEntity<>(formRecordService.createFormRecord(request), HttpStatus.OK);
    }

    @GetMapping("/form")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_EMPLOYEE')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(
            responseCode = "200",
            description = "List of form records",
            content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FormRecordDTO.class)))}
    )
    public ResponseEntity<PagedResponse> getFormRecordList(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                           @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
                                                           @RequestParam(value = "sortBy", defaultValue = "+id") String sortBy) {
        return new ResponseEntity<>(formRecordService.getFormRecordList(pageNo, pageSize, sortBy), HttpStatus.OK);
    }

    @GetMapping("/form/managed")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(
            responseCode = "200",
            description = "List of form records",
            content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FormRecordDTO.class)))}
    )
    public ResponseEntity<PagedResponse> getSubordinatesRecordList(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                               @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
                                               @RequestParam(value = "sortBy", defaultValue = "+id") String sortBy) {
        return new ResponseEntity<>(formRecordService.getSubordinatesFormList(pageNo, pageSize, sortBy), HttpStatus.OK);
    }

    @GetMapping("/form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_EMPLOYEE')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<FormRecordDTO> getSingleFormRecord(@PathVariable("id") int formId) {
        return new ResponseEntity<>(formRecordService.getSingleFormRecord(formId), HttpStatus.OK);
    }

    @PutMapping("/form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_EMPLOYEE')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<FormRecordDTO> updateFormRecord(@RequestBody @Valid FormRecordDTO request) {
        return new ResponseEntity<>(formRecordService.updateFormRecord(request), HttpStatus.OK);
    }

    @PutMapping("/form/process/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<FormRecordDTO> processFormRecord(@RequestBody @Valid FormRecordDTO request) {
        return new ResponseEntity<>(formRecordService.processFormRecord(request), HttpStatus.OK);
    }

    @DeleteMapping("/form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_EMPLOYEE')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> deleteFormRecord(@PathVariable("id") int formId) {
        formRecordService.deleteFormRecord(formId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
