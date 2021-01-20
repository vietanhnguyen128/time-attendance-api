package com.example.TimeAttendanceAPI.Service;

import com.example.TimeAttendanceAPI.Model.Attendance;
import com.example.TimeAttendanceAPI.Model.Employee;
import com.example.TimeAttendanceAPI.Model.FormRecord;
import org.apache.tomcat.jni.Local;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

public interface GeneralService {

    abstract Attendance createAttendanceRecord(Attendance record);

    abstract FormRecord createForm(FormRecord form);

    abstract Duration getAttendanceTimeByDay(Integer employeeId, LocalDate date);

    abstract Duration getAttendanceTimeByPeriod(Integer employeeId, LocalDate startDate, LocalDate endDate);

    abstract Duration getTotalAttendanceTime(Integer employeeId);

    abstract Duration getTotalLateTime(Integer employeeId);

    abstract Duration getLateTimeByPeriod(Integer employeeId, LocalDate startDate, LocalDate endDate);

    abstract Duration getTotalWorkingTime(Integer employeeId);

    abstract Duration getWorkingTimeByPeriod(Integer employeeId, LocalDate startDate, LocalDate endDate);

    abstract Duration getTotalAbsentTime(Integer employeeId);

    abstract Duration getAbsentTimeByPeriod(Integer employeeId, LocalDate startDate, LocalDate endDate);

    abstract Duration getTotalOvertime(Integer employeeId);

    abstract Duration getOvertimeByPeriod(Integer employeeId, LocalDate startDate, LocalDate endDate);
}
