package com.example.TimeAttendanceAPI.controller.form_record;

import com.example.TimeAttendanceAPI.dto.FormRecordDTO;
import com.example.TimeAttendanceAPI.service.form_record.FormRecordService;
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

    @PostMapping("/form/new")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> createForm(FormRecordDTO request) {
        return new ResponseEntity<>(formRecordService.createFormRecord(request), HttpStatus.OK);
    }

    @GetMapping("/form/all")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> getFormRecordList(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize, @RequestParam("sortBy") String sortBy) {
        return new ResponseEntity<>(formRecordService.getFormRecordList(pageNo, pageSize, sortBy), HttpStatus.OK);
    }

    @GetMapping("/form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> getSingleFormRecord(@PathVariable("id") int formId) {
        return new ResponseEntity<>(formRecordService.getSingleFormRecord(formId), HttpStatus.OK);
    }

    @PutMapping("/form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> updateFormRecord(@RequestBody @Valid FormRecordDTO request) {
        return new ResponseEntity<>(formRecordService.updateFormRecord(request), HttpStatus.OK);
    }

    @PutMapping("/form/{id}/process")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> processFormRecord(@RequestBody @Valid FormRecordDTO request) {
        return new ResponseEntity<>(formRecordService.processFormRecord(request), HttpStatus.OK);
    }

    @DeleteMapping("/form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> deleteFormRecord(@PathVariable("id") int formId) {
        formRecordService.deleteFormRecord(formId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
