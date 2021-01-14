package com.example.TimeAttendanceAPI.Service;

import com.example.TimeAttendanceAPI.Model.Attendance;
import com.example.TimeAttendanceAPI.Model.Employee;
import com.example.TimeAttendanceAPI.Model.FormRecord;

import java.time.Duration;
import java.util.ArrayList;

public interface GeneralService {

    Attendance createAttendanceRecord(Attendance record);

    FormRecord createForm(FormRecord form);

    Duration getTotalAttendanceTime(Integer employeeId);

    Duration getLateTimeByMonth(Integer employeeId, Integer month);

    Duration getTotalWorkingTime(Integer employeeId);

    Duration getTotalAbsentTime(Integer employeeId);

    Duration getTotalOvertime(Integer employeeId);
}
