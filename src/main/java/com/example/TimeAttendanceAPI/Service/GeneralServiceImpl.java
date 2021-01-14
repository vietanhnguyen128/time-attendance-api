package com.example.TimeAttendanceAPI.Service;

import com.example.TimeAttendanceAPI.Model.Attendance;
import com.example.TimeAttendanceAPI.Model.Employee;
import com.example.TimeAttendanceAPI.Model.FormRecord;
import com.example.TimeAttendanceAPI.Repository.AttendanceRepository;
import com.example.TimeAttendanceAPI.Repository.EmployeeRepository;
import com.example.TimeAttendanceAPI.Repository.FormRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

@Service
public class GeneralServiceImpl implements GeneralService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private FormRecordRepository formRecordRepository;

    @Override
    public Attendance createAttendanceRecord(Attendance record) {
        return attendanceRepository.save(record);
    }

    @Override
    public FormRecord createForm(FormRecord form) {
        return formRecordRepository.save(form);
    }

    @Override
    public Duration getTotalAttendanceTime(Integer employeeId) {
        if (employeeRepository.findById(employeeId).isPresent()) {

            ArrayList<Attendance> result = attendanceRepository.getAttendanceDetail(employeeId);
            Duration total = Duration.ZERO;
            for (int i = 0; i < result.size(); i+=2) {
                total = total.plus(Duration.between(result.get(i+1).getTimeRecord(), result.get(i).getTimeRecord()));
            }

            return total;
        }

        return null;
    }

    @Override
    public Duration getLateTimeByMonth(Integer employeeId, Integer month) {
        if (attendanceRepository.findById(employeeId).isPresent()) {

            ArrayList<Attendance> result = attendanceRepository.getAttendanceDetailByMonth(employeeId, month);
            Duration total = Duration.ZERO;
            LocalTime startTime = employeeRepository.getOne(employeeId).getStartTime();
            for (int i = 0; i < result.size(); i += 2) {
                Duration temp = Duration.between(startTime, result.get(i).getTimeRecord());
                if (!temp.isNegative()) {
                    total = total.plus(temp);
                }
            }

            return total;
        }

        return null;
    }

    @Override
    public Duration getTotalWorkingTime(Integer employeeId) {
        return getTotalAttendanceTime(employeeId);
    }

    @Override
    public Duration getTotalAbsentTime(Integer employeeId) {
        if (employeeRepository.findById(employeeId).isPresent()) {
            ArrayList<FormRecord> result = formRecordRepository.getApprovedForms(employeeId, "absent");
            Duration total = Duration.ZERO;

            for (int i = 0; i < result.size(); i++) {
                total = total.plus(Duration.between(LocalTime.MIN, result.get(i).getTimePeriod()));
            }

            return total;
        }

        return null;
    }

    @Override
    public Duration getTotalOvertime(Integer employeeId) {
        return null;
    }
}
