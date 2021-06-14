package com.example.TimeAttendanceAPI.service.attendance;

import com.example.TimeAttendanceAPI.dto.AttendanceInfo;
import com.example.TimeAttendanceAPI.dto.AttendanceRecordDTO;
import com.example.TimeAttendanceAPI.model.AttendanceRecord;
import org.springframework.data.domain.Page;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    void checkingIn(int userId);

    void checkingOut(int userId);

    AttendanceInfo getAttendanceInfo(int userId, int month, int year);

    List<AttendanceRecordDTO> getAttendanceRecords(int userId, int month, int year);
}
