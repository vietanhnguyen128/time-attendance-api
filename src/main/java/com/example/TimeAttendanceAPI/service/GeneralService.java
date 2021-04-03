package com.example.TimeAttendanceAPI.service;

import com.example.TimeAttendanceAPI.model.AttendanceRecord;
import com.example.TimeAttendanceAPI.model.FormRecord;

import java.time.Duration;

public interface GeneralService {

    AttendanceRecord createAttendanceRecord(AttendanceRecord record);

    FormRecord createForm(FormRecord form);

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
