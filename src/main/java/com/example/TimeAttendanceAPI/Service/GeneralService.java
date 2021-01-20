package com.example.TimeAttendanceAPI.Service;

import com.example.TimeAttendanceAPI.Model.Attendance;
import com.example.TimeAttendanceAPI.Model.Employee;
import com.example.TimeAttendanceAPI.Model.FormRecord;
import org.apache.tomcat.jni.Local;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

public interface GeneralService {

    Attendance createAttendanceRecord(Attendance record);

    FormRecord createForm(FormRecord form);

    Duration getAttendanceTimeByDay(Integer employeeId, LocalDate date);

    Duration getAttendanceTimeByPeriod(Integer employeeId, LocalDate startDate, LocalDate endDate);

    Duration getTotalAttendanceTime(Integer employeeId);

    Duration getTotalLateTime(Integer employeeId);

    Duration getLateTimeByPeriod(Integer employeeId, LocalDate startDate, LocalDate endDate);

    Duration getTotalWorkingTime(Integer employeeId);

    Duration getWorkingTimeByPeriod(Integer employeeId, LocalDate startDate, LocalDate endDate);

    Duration getTotalAbsentTime(Integer employeeId);

    Duration getAbsentTimeByPeriod(Integer employeeId, LocalDate startDate, LocalDate endDate);

    Duration getTotalOvertime(Integer employeeId);

    Duration getOvertimeByPeriod(Integer employeeId, LocalDate startDate, LocalDate endDate);
}
