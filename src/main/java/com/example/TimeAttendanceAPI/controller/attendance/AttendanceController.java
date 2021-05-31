package com.example.TimeAttendanceAPI.controller.attendance;

import com.example.TimeAttendanceAPI.dto.AttendanceInfo;
import com.example.TimeAttendanceAPI.dto.AttendanceRecordDTO;
import com.example.TimeAttendanceAPI.service.attendance.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @PostMapping("/attendance/check-in")
    public ResponseEntity<String> checkIn(@RequestBody @Valid AttendanceRecordDTO checkIn) {
        attendanceService.checkingIn(checkIn);
        return new ResponseEntity<>("Current time: " + LocalDateTime.now().format(formatter),HttpStatus.OK);
    }

    @PostMapping("/attendance/check-out")
    public ResponseEntity<String> checkOut(@RequestBody @Valid AttendanceRecordDTO checkOut) {
        attendanceService.checkingOut(checkOut);
        return new ResponseEntity<>("Current time: " + LocalDateTime.now().format(formatter), HttpStatus.OK);
    }

    @GetMapping("/attendance/list/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_EMPLOYEE')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<AttendanceRecordDTO>> getAttendanceList(@PathVariable("id") int userId, @RequestParam int month, @RequestParam int year) {
        List<AttendanceRecordDTO> result = attendanceService.getAttendanceRecords(userId, month, year);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/attendance/info/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_EMPLOYEE')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<AttendanceInfo> getAttendanceInfo(@PathVariable("id") int userId, @RequestParam int month, @RequestParam int year) {
        AttendanceInfo result = attendanceService.getAttendanceInfo(userId, month, year);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
