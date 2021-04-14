package com.example.TimeAttendanceAPI.service.attendance;

import com.example.TimeAttendanceAPI.dto.AttendanceRecordDTO;

import java.time.Duration;
import java.util.List;

public interface AttendanceService {
    AttendanceRecordDTO createAttendanceRecord(AttendanceRecordDTO AttendanceRecordDTO);

    List<AttendanceRecordDTO> getAttendanceRecordList();

    AttendanceRecordDTO getSingleAttendanceRecord(String AttendanceRecordId);

    AttendanceRecordDTO updateAttendanceRecord(AttendanceRecordDTO AttendanceRecordDTO);

    void deleteAttendanceRecord(String AttendanceRecordId);

    Duration getAttendanceTimeByDay(Integer employeeId, String date);

    Duration getAttendanceTimeByPeriod(Integer employeeId, String startDate, String endDate);

    Duration getTotalAttendanceTime(Integer employeeId);

    Duration getTotalLateTime(Integer employeeId);

    Duration getLateTimeByPeriod(Integer employeeId, String startDate, String endDate);

    Duration getTotalWorkingTime(Integer employeeId);

    Duration getWorkingTimeByPeriod(Integer employeeId, String startDate, String endDate);

    Duration getTotalAbsentTime(Integer employeeId);

    Duration getAbsentTimeByPeriod(Integer employeeId, String startDate, String endDate);

    Duration getTotalOvertime(Integer employeeId);

    Duration getOvertimeByPeriod(Integer employeeId, String startDate, String endDate);
}
