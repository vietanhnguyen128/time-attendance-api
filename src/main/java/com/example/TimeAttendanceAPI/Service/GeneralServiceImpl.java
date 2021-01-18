package com.example.TimeAttendanceAPI.Service;

import com.example.TimeAttendanceAPI.Model.Attendance;
import com.example.TimeAttendanceAPI.Model.Employee;
import com.example.TimeAttendanceAPI.Model.FormRecord;
import com.example.TimeAttendanceAPI.Repository.AttendanceRepository;
import com.example.TimeAttendanceAPI.Repository.EmployeeRepository;
import com.example.TimeAttendanceAPI.Repository.FormRecordRepository;
import org.apache.tomcat.jni.Local;
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
        Employee info = employeeRepository.getOne(input.get(0).getEmployeeId());
        int shiftStart = info.getShiftStart().toSecondOfDay();
        int shiftEnd = info.getShiftEnd().toSecondOfDay();
        int breakStart = info.getBreakStart().toSecondOfDay();
        int breakEnd = info.getBreakEnd().toSecondOfDay();

        int total = 0;
        for (int i = 0; i < input.size(); i+=2) {
            int checkIn = input.get(i).getTimeRecord().toSecondOfDay();
            int checkOut = input.get(i+1).getTimeRecord().toSecondOfDay();

            if (checkIn < shiftStart) {
                if (checkOut < shiftStart)
                    continue;
                else if (checkOut >= breakStart && checkOut < breakEnd) {
                    total += (shiftStart - breakStart);
                } else if (checkOut >= breakEnd && checkOut < shiftEnd) {
                    int seconds = checkOut - shiftStart - (breakEnd - breakStart);
                    total += seconds;
                } else {
                    int seconds = shiftEnd - shiftStart - (breakEnd - breakStart);
                    total += seconds;
                }
            } else if (checkIn < breakStart) {
                if (checkOut < breakStart) {
                    total += checkOut - checkIn;
                } else if (checkOut < breakEnd) {
                    total += breakStart - checkIn;
                } else if (checkOut >= breakEnd && checkOut < shiftEnd) {
                    int seconds = checkOut - checkIn - (breakEnd - breakStart);
                    total += seconds;
                } else {
                    int seconds = shiftEnd - checkIn - (breakEnd - breakStart);
                    total += seconds;
                }
            } else if (checkIn >= breakStart && checkIn < breakEnd) {
                if (checkOut < breakEnd) {
                    continue;
                } else if (checkOut >= breakEnd && checkOut < shiftEnd) {
                    total += checkOut - breakEnd;
                } else {
                    total += shiftEnd - breakEnd;
                }
            } else if (checkIn >= breakEnd && checkIn < shiftEnd) {
                if (checkOut >= shiftEnd) {
                    total += shiftEnd - checkIn;
                } else {
                    total += checkOut - checkIn;
                }
            } else {
                continue;
            }
        }

        return Duration.ofSeconds(total);
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
            Duration total = calculateAttendanceTime(attendanceRepository.getAttendanceDetailByDay(employeeId, date));
            return total;
        }
        return null;
    }

    @Override
    public Duration getAttendanceTimeByPeriod(Integer employeeId, LocalDate startDate, LocalDate endDate) {
        if (attendanceRepository.findById(employeeId).isPresent()) {
            Duration total = calculateAttendanceTime(attendanceRepository.getAttendanceDetailByPeriod(employeeId, startDate, endDate));
            return total;
        }
        return null;
    }

    @Override
    public Duration getTotalAttendanceTime(Integer employeeId) {
        if (employeeRepository.findById(employeeId).isPresent()) {
            Duration total = calculateAttendanceTime(attendanceRepository.getAttendanceDetail(employeeId));
            return total;
        }

        return null;
    }

    @Override
    public Duration getLateTimeByPeriod(Integer employeeId, LocalDate startDate, LocalDate endDate) {
        if (attendanceRepository.findById(employeeId).isPresent()) {

            ArrayList<Attendance> records = attendanceRepository.getAttendanceDetailByPeriod(employeeId, startDate, endDate);
            Duration total = Duration.ZERO;
            LocalTime startTime = employeeRepository.getOne(employeeId).getShiftStart();
            for (int i = 0; i < records.size(); i += 2) {
                Duration temp = Duration.between(startTime, records.get(i).getTimeRecord());
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
        Duration totalAttendance = getTotalAttendanceTime(employeeId);
        Duration totalOvertime = getTotalOvertime(employeeId);
        return totalAttendance.plus(totalOvertime);
    }

    @Override
    public Duration getTotalAbsentTime(Integer employeeId) {
        if (employeeRepository.findById(employeeId).isPresent()) {
            ArrayList<FormRecord> records = formRecordRepository.getApprovedForms(employeeId, "absent");
            Duration total = Duration.ZERO;

            for (int i = 0; i < records.size(); i++) {
                total = total.plus(Duration.between(LocalTime.MIN, records.get(i).getTimePeriod()));
            }

            return total;
        }

        return null;
    }

    @Override
    public Duration getTotalOvertime(Integer employeeId) {
        if (employeeRepository.findById(employeeId).isPresent()) {
            ArrayList<FormRecord> records = formRecordRepository.getApprovedForms(employeeId, "overtime");
            Duration total = Duration.ZERO;

            for (int i = 0; i < records.size(); i++) {
                total = total.plus(Duration.between(LocalTime.MIN, records.get(i).getTimePeriod()));
            }

            return total;
        }

        return null;
    }
}
