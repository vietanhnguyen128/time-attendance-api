package com.example.TimeAttendanceAPI.service.attendance;

import com.example.TimeAttendanceAPI.dto.AttendanceRecordDTO;

import java.util.List;

public interface AttendanceService {
    AttendanceRecordDTO createAttendanceRecord(AttendanceRecordDTO AttendanceRecordDTO);

    List<AttendanceRecordDTO> getAttendanceRecordList();

    AttendanceRecordDTO getSingleAttendanceRecord(String AttendanceRecordId);

    AttendanceRecordDTO updateAttendanceRecord(AttendanceRecordDTO AttendanceRecordDTO);

    void deleteAttendanceRecord(String AttendanceRecordId);
}
