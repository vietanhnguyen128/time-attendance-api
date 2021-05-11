package com.example.TimeAttendanceAPI.controller.attendance;

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
import java.util.List;

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

    @GetMapping("/attendance/info/{id}")
    public ResponseEntity<?> getAttendanceInfo(@PathVariable("id") int userId, @RequestParam int month, @RequestParam int year) {
        List<AttendanceRecordDTO> result = attendanceService.getAttendanceRecords(userId, month, year);
        return ResponseEntity.ok(result);
    }
}
