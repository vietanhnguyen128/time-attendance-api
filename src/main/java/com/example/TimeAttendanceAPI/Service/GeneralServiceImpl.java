package com.example.TimeAttendanceAPI.Service;

import com.example.TimeAttendanceAPI.Model.Attendance;
import com.example.TimeAttendanceAPI.Model.FormRecord;
import com.example.TimeAttendanceAPI.Repository.AttendanceRepository;
import com.example.TimeAttendanceAPI.Repository.EmployeeRepository;
import com.example.TimeAttendanceAPI.Repository.FormRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private Duration calculateAttendanceTime(ArrayList<Attendance> input) {
        LocalTime breakStart = LocalTime.of(11, 45);
        LocalTime breakEnd = LocalTime.of(13, 0);

        ArrayList<Attendance> result = input;
        Duration total = Duration.ZERO;
        for (int i = 0; i < result.size(); i+=2) {
            LocalTime checkIn = result.get(i).getTimeRecord();
            LocalTime checkOut = result.get(i+1).getTimeRecord();

            if (checkIn.isAfter(breakStart) && checkIn.isBefore(breakEnd) && checkOut.isAfter(breakStart) && checkOut.isBefore(breakEnd)) {
                continue;
            } else if (checkOut.isAfter(breakStart) && checkOut.isBefore(breakEnd)) {
                total = total.plus(Duration.between(result.get(i+1).getTimeRecord(), breakStart));
                continue;
            } else if (checkIn.isAfter(breakStart) && checkIn.isBefore(breakEnd)) {
                total = total.plus(Duration.between(breakEnd, result.get(i).getTimeRecord()));
                continue;
            }

            total = total.plus(Duration.between(result.get(i+1).getTimeRecord(), result.get(i).getTimeRecord()));
        }

        return total;
    }

    @Override
    public Attendance createAttendanceRecord(Attendance record) {
        if (record.getDateRecord() == null)
            record.setDateRecord(LocalDate.now());
        if (record.getTimeRecord() == null)
            record.setTimeRecord(LocalTime.now());
        return attendanceRepository.save(record);
    }

    @Override
    public FormRecord createForm(FormRecord form) {
        if (form.getCreatedAt() == null)
            form.setCreatedAt(LocalDateTime.now());
        return formRecordRepository.save(form);
    }

    @Override
    public Duration getAttendanceTimeByDay(Integer employeeId, LocalDate date) {
        if (employeeRepository.findById(employeeId).isPresent()) {
            return calculateAttendanceTime(attendanceRepository.getAttendanceDetailByDay(employeeId, date));
        }
        return null;
    }

    @Override
    public Duration getTotalAttendanceTime(Integer employeeId) {
        if (employeeRepository.findById(employeeId).isPresent()) {
            return calculateAttendanceTime(attendanceRepository.getAttendanceDetail(employeeId));
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
        if (employeeRepository.findById(employeeId).isPresent()) {
            ArrayList<FormRecord> result = formRecordRepository.getApprovedForms(employeeId, "overtime");
            Duration total = Duration.ZERO;

            for (int i = 0; i < result.size(); i++) {
                total = total.plus(Duration.between(LocalTime.MIN, result.get(i).getTimePeriod()));
            }

            return total;
        }

        return null;
    }
}
