package com.example.TimeAttendanceAPI.controller.attendance;

import com.example.TimeAttendanceAPI.dto.AttendanceRecordDTO;
import com.example.TimeAttendanceAPI.service.attendance.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping("/attendance/check-in")
    public ResponseEntity<?> checkIn(@RequestBody @Valid AttendanceRecordDTO checkIn) {
        attendanceService.checkingIn(checkIn);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/attendance/check-out")
    public ResponseEntity<?> checkOut(@RequestBody @Valid AttendanceRecordDTO checkOut) {
        attendanceService.checkingOut(checkOut);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
