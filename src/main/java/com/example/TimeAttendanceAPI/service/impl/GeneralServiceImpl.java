package com.example.TimeAttendanceAPI.service.impl;

import com.example.TimeAttendanceAPI.model.Attendance;
import com.example.TimeAttendanceAPI.model.Employee;
import com.example.TimeAttendanceAPI.model.FormRecord;
import com.example.TimeAttendanceAPI.repository.AttendanceRepository;
import com.example.TimeAttendanceAPI.repository.EmployeeRepository;
import com.example.TimeAttendanceAPI.repository.FormRecordRepository;
import com.example.TimeAttendanceAPI.service.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class GeneralServiceImpl implements GeneralService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private FormRecordRepository formRecordRepository;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private Duration calculateAttendanceTime(ArrayList<Attendance> input) {
        Employee info = employeeRepository.getOne(input.get(0).getEmployeeId());
        int shiftStart = 8;
        int shiftEnd = 17;
        int breakStart = 12;
        int breakEnd = 13;

        int total = 0;
        for (int i = 0; i < input.size(); i+=2) {
            int checkIn = input.get(i).getTimeRecord().toSecondOfDay();
            int checkOut = input.get(i+1).getTimeRecord().toSecondOfDay();

            if (checkIn < shiftStart) {
                if (checkOut < shiftStart) {
                }
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
        if (form.getStatus() == null)
            form.setStatus("pending");
        return formRecordRepository.save(form);
    }

    @Override
    public Duration getAttendanceTimeByDay(Integer employeeId, String date) {
        if (employeeRepository.findById(employeeId).isPresent()) {
            LocalDate convertedDate = LocalDate.parse(date, dateTimeFormatter);
            return calculateAttendanceTime(attendanceRepository.getAttendanceDetailByDay(employeeId, convertedDate));
        }
        return null;
    }

    @Override
    public Duration getAttendanceTimeByPeriod(Integer employeeId, String startDate, String endDate) {
        if (attendanceRepository.findById(employeeId).isPresent()) {
            LocalDate convertedStartDate = LocalDate.parse(startDate, dateTimeFormatter);
            LocalDate convertedEndDate = LocalDate.parse(endDate, dateTimeFormatter);
            return calculateAttendanceTime(attendanceRepository.getAttendanceDetailByPeriod(employeeId, convertedStartDate, convertedEndDate));
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
    public Duration getTotalLateTime(Integer employeeId) {
        if (employeeRepository.findById(employeeId).isPresent()) {

            ArrayList<Attendance> records = attendanceRepository.getAttendanceDetail(employeeId);
            Duration total = Duration.ZERO;
            LocalTime startTime = LocalTime.of(8, 0);
            LocalDate currentDate;
            LocalDate previousDate = LocalDate.MIN;

            for (int i = 0; i < records.size(); i += 2) {
                currentDate = records.get(i).getDateRecord();
                if (previousDate.equals(currentDate)) {
                    continue;
                }
                Duration temp = Duration.between(startTime, records.get(i).getTimeRecord());
                if (!temp.isNegative()) {
                    total = total.plus(temp);
                }
                previousDate = currentDate;
            }

            return total;
        }

        return null;
    }

    @Override
    public Duration getLateTimeByPeriod(Integer employeeId, String startDate, String endDate) {
        if (employeeRepository.findById(employeeId).isPresent()) {

            LocalDate convertedStartDate = LocalDate.parse(startDate, dateTimeFormatter);
            LocalDate convertedEndDate = LocalDate.parse(endDate, dateTimeFormatter);

            ArrayList<Attendance> records = attendanceRepository.getAttendanceDetailByPeriod(employeeId, convertedStartDate, convertedEndDate);
            Duration total = Duration.ZERO;
            LocalTime startTime = LocalTime.of(8, 0);
            LocalDate currentDate;
            LocalDate previousDate = LocalDate.MIN;

            for (int i = 0; i < records.size(); i += 2) {
                currentDate = records.get(i).getDateRecord();
                if (previousDate.equals(currentDate)) {
                    continue;
                }
                Duration temp = Duration.between(startTime, records.get(i).getTimeRecord());
                if (!temp.isNegative()) {
                    total = total.plus(temp);
                }
                previousDate = currentDate;
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
    public Duration getWorkingTimeByPeriod(Integer employeeId, String startDate, String endDate) {
        Duration totalAttendance = getAttendanceTimeByPeriod(employeeId, startDate, endDate);
        Duration totalOvertime = getOvertimeByPeriod(employeeId, startDate, endDate);
        return totalAttendance.plus(totalOvertime);
    }

    @Override
    public Duration getTotalAbsentTime(Integer employeeId) {
        if (employeeRepository.findById(employeeId).isPresent()) {
            List<FormRecord> records = formRecordRepository.getApprovedForms(employeeId, "absent");
            Duration total = Duration.ZERO;

            for (FormRecord record : records) {
                total = total.plus(Duration.between(LocalTime.MIN, record.getTimePeriod()));
            }

            return total;
        }

        return null;
    }

    @Override
    public Duration getAbsentTimeByPeriod(Integer employeeId, String startDate, String endDate) {
        if (employeeRepository.findById(employeeId).isPresent()) {
            LocalDate convertedStartDate = LocalDate.parse(startDate, dateTimeFormatter);
            LocalDate convertedEndDate = LocalDate.parse(endDate, dateTimeFormatter);

            List<FormRecord> records = formRecordRepository.getApprovedFormsByPeriod(employeeId, "absent", convertedStartDate, convertedEndDate);
            Duration total = Duration.ZERO;

            for (FormRecord record : records) {
                total = total.plus(Duration.between(LocalTime.MIN, record.getTimePeriod()));
            }

            return total;
        }

        return null;
    }

    @Override
    public Duration getTotalOvertime(Integer employeeId) {
        if (employeeRepository.findById(employeeId).isPresent()) {
            List<FormRecord> records = formRecordRepository.getApprovedForms(employeeId, "overtime");
            Duration total = Duration.ZERO;

            for (FormRecord record : records) {
                total = total.plus(Duration.between(LocalTime.MIN, record.getTimePeriod()));
            }

            return total;
        }

        return null;
    }

    @Override
    public Duration getOvertimeByPeriod(Integer employeeId, String startDate, String endDate) {
        if (employeeRepository.findById(employeeId).isPresent()) {
            LocalDate convertedStartDate = LocalDate.parse(startDate, dateTimeFormatter);
            LocalDate convertedEndDate = LocalDate.parse(endDate, dateTimeFormatter);

            List<FormRecord> records = formRecordRepository.getApprovedFormsByPeriod(employeeId, "overtime", convertedStartDate, convertedEndDate);
            Duration total = Duration.ZERO;

            for (FormRecord record : records) {
                total = total.plus(Duration.between(LocalTime.MIN, record.getTimePeriod()));
            }

            return total;
        }

        return null;
    }
}
