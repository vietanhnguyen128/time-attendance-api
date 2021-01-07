package com.example.TimeAttendanceAPI.Service;

import com.example.TimeAttendanceAPI.Model.Absent;
import com.example.TimeAttendanceAPI.Model.Attendance;
import com.example.TimeAttendanceAPI.Model.DetailByMonth;
import com.example.TimeAttendanceAPI.Repository.AttendanceRepository;
import com.example.TimeAttendanceAPI.Repository.DetailByMonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

@Service
public class GeneralServiceImpl implements GeneralService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private DetailByMonthRepository detailByMonthRepository;

    @Override
    public Integer getTotalWorkTimeOfMonth(Integer id, Month month) {
        List<Attendance> employeeAttendance = attendanceRepository.findByIdAndMonth(id, month);
        int totalWorkTime = 0;

        for (Attendance a : employeeAttendance) {
            totalWorkTime = a.getEndTime() - a.getStartTime();
        }

        return totalWorkTime;
    }

    @Override
    public Integer getTotalLateTimeOfMonth(Integer id, Month month) {
        List<Attendance> employeeAttendance = attendanceRepository.findByIdAndMonth(id, month);
        int defaultStartTime = detailByMonthRepository.findByEmployeeIdAndMonth(id, month).getStartTime();
        int totalLateTime = 0;

        for (Attendance a : employeeAttendance) {
            totalLateTime = a.getStartTime() - defaultStartTime;
        }

        return totalLateTime;
    }

    @Override
    public List<Attendance> getAttendanceDetailOfMonth(Integer id, Month month) {
        return attendanceRepository.findByIdAndMonth(id, month);
    }

    @Override
    public Attendance getAttendanceDetailOfDay(Integer id, LocalDate date) {
        return attendanceRepository.findByIdAndDay(id, date);
    }

    @Override
    public Absent submitAbsent(Absent absent) {
        return null;
    }
}
