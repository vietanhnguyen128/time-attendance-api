package com.example.TimeAttendanceAPI.Service;

import com.example.TimeAttendanceAPI.Model.Absent;
import com.example.TimeAttendanceAPI.Model.Attendance;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

public interface GeneralService {
    Integer getTotalWorkTimeOfMonth(Integer id, Month month);
    Integer getTotalLateTimeOfMonth(Integer id, Month month);
    List<Attendance> getAttendanceDetailOfMonth(Integer id, Month month);
    Attendance getAttendanceDetailOfDay(Integer id, LocalDate date);
    Absent submitAbsent(Absent absent);
}
