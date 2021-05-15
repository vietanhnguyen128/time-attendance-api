package com.example.TimeAttendanceAPI.controller.attendance;

import com.example.TimeAttendanceAPI.dto.AttendanceInfo;
import com.example.TimeAttendanceAPI.dto.AttendanceRecordDTO;
import com.example.TimeAttendanceAPI.model.AttendanceRecord;
import com.example.TimeAttendanceAPI.service.attendance.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @PostMapping("/attendance/check-in")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public ResponseEntity<?> checkIn(@RequestBody @Valid AttendanceRecordDTO checkIn) {
        attendanceService.checkingIn(checkIn);
        return new ResponseEntity<>("Current time: " + LocalDateTime.now().format(formatter),HttpStatus.OK);
    }

    @PostMapping("/attendance/check-out")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public ResponseEntity<?> checkOut(@RequestBody @Valid AttendanceRecordDTO checkOut) {
        attendanceService.checkingOut(checkOut);
        return new ResponseEntity<>("Current time: " + LocalDateTime.now().format(formatter), HttpStatus.OK);
    }

    @GetMapping("/attendance/list/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public ResponseEntity<?> getAttendanceList(@PathVariable("id") int userId, @RequestParam int month, @RequestParam int year) {
        List<AttendanceRecordDTO> result = attendanceService.getAttendanceRecords(userId, month, year);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/attendance/info/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public ResponseEntity<?> getAttendanceInfo(@PathVariable("id") int userId, @RequestParam int month, @RequestParam int year) {
        AttendanceInfo result = attendanceService.getAttendanceInfo(userId, month, year);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
