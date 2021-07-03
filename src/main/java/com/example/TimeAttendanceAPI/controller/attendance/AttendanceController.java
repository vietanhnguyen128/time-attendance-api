package com.example.TimeAttendanceAPI.controller.attendance;

import com.example.TimeAttendanceAPI.dto.AttendanceInfo;
import com.example.TimeAttendanceAPI.dto.AttendanceRecordDTO;
import com.example.TimeAttendanceAPI.service.attendance.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @PostMapping("/attendance/check-in")
    @Operation(summary = "Check in", description = "Điểm danh khi đến công ty")
    public ResponseEntity<String> checkIn(@Parameter(description = "Id của người dùng") @RequestParam int userId) {
        attendanceService.checkingIn(userId);
        return new ResponseEntity<>("Current time: " + LocalDateTime.now().format(formatter),HttpStatus.OK);
    }

    @PostMapping("/attendance/check-out")
    @Operation(summary = "Check out", description = "Điểm danh khi rời công ty")
    public ResponseEntity<String> checkOut(@Parameter(description = "Id của người dùng") @RequestParam int userId) {
        attendanceService.checkingOut(userId);
        return new ResponseEntity<>("Current time: " + LocalDateTime.now().format(formatter), HttpStatus.OK);
    }

    @GetMapping("/attendance/list/{id}")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Lấy danh sách lượt chấm công",
            description = "Lấy danh sách lượt chấm công")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<List<AttendanceRecordDTO>> getAttendanceList(@Parameter(description = "Id của người dùng") @PathVariable("id") int userId,
                                                                       @Parameter(description = "Tháng") @RequestParam int month,
                                                                       @Parameter(description = "Năm") @RequestParam int year) {
        List<AttendanceRecordDTO> result = attendanceService.getAttendanceRecords(userId, month, year);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/attendance/info/{id}")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Thống kê thông tin chấm công",
            description = "Thống kê thông tin chấm công")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<AttendanceInfo> getAttendanceInfo(@Parameter(description = "Id của người dùng") @PathVariable("id") int userId,
                                                            @Parameter(description = "Tháng") @RequestParam int month,
                                                            @Parameter(description = "Năm") @RequestParam int year) {
        AttendanceInfo result = attendanceService.getAttendanceInfo(userId, month, year);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
