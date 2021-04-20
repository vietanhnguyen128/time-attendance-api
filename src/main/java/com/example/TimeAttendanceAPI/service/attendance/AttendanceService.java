package com.example.TimeAttendanceAPI.service.attendance;

import com.example.TimeAttendanceAPI.dto.AttendanceRecordDTO;
import org.springframework.data.domain.Page;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    Page<AttendanceRecordDTO> getAttendanceRecordList(int pageNo, int pageSize, String sortBy);

    Page<AttendanceRecordDTO> getAttendanceRecordListOfMonth(int pageNo, int pageSize, String sortBy, LocalDate date);

    AttendanceRecordDTO updateAttendanceRecord(AttendanceRecordDTO AttendanceRecordDTO);

    void deleteAttendanceRecord(String AttendanceRecordId);
}
