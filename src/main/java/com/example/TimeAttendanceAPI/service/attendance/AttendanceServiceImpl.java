package com.example.TimeAttendanceAPI.service.attendance;

import com.example.TimeAttendanceAPI.dto.AttendanceInfo;
import com.example.TimeAttendanceAPI.dto.AttendanceRecordDTO;
import com.example.TimeAttendanceAPI.model.AttendanceCache;
import com.example.TimeAttendanceAPI.model.AttendanceRecord;
import com.example.TimeAttendanceAPI.model.DateInfo;
import com.example.TimeAttendanceAPI.model.DateOfMonth;
import com.example.TimeAttendanceAPI.model.FormRecord;
import com.example.TimeAttendanceAPI.model.Holiday;
import com.example.TimeAttendanceAPI.model.User;
import com.example.TimeAttendanceAPI.model._enum.FormStatus;
import com.example.TimeAttendanceAPI.model._enum.FormType;
import com.example.TimeAttendanceAPI.repository.AttendanceCacheRepository;
import com.example.TimeAttendanceAPI.repository.AttendanceRepository;
import com.example.TimeAttendanceAPI.repository.FormRecordRepository;
import com.example.TimeAttendanceAPI.repository.HolidayRepository;
import com.example.TimeAttendanceAPI.repository.UserRepository;
import com.example.TimeAttendanceAPI.security.service.CustomUserDetails;
import com.example.TimeAttendanceAPI.service.form_record.FormRecordService;
import com.example.TimeAttendanceAPI.utils.ConversionUtils;
import com.example.TimeAttendanceAPI.utils.PageableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceCacheRepository attendanceCacheRepository;
    private final FormRecordRepository formRecordRepository;
    private final HolidayRepository holidayRepository;

    @Override
    public void checkingIn(AttendanceRecordDTO checkIn) {
        Optional<User> userOpt = userRepository.findById(checkIn.getUserId());
        Optional<AttendanceCache> attendanceCacheOpt = attendanceCacheRepository.findByUser_UserId(checkIn.getUserId());
        if (userOpt.isPresent() && attendanceCacheOpt.isPresent()) {
            AttendanceCache attendanceCache = attendanceCacheOpt.get();
            if (!attendanceCache.isCheckIn()) { //if previous record is not check in
                AttendanceRecord checkInRecord = AttendanceRecord.builder()
                        .user(userOpt.get())
                        .date(LocalDate.now())
                        .checkInTimestamp(LocalTime.now())
                        .build();

                checkInRecord = attendanceRepository.save(checkInRecord);

                attendanceCache.setCheckIn(true);
                attendanceCache.setLastRecordDate(LocalDate.now());
                attendanceCache.setLastRecordId(checkInRecord.getId());

                attendanceCacheRepository.save(attendanceCache);
            } else if (attendanceCache.getLastRecordDate().isBefore(LocalDate.now())) {
                AttendanceRecord currentRecord = attendanceRepository.getOne(attendanceCache.getLastRecordId());

                currentRecord.setDate(LocalDate.now());
                currentRecord.setCheckInTimestamp(LocalTime.now());

                attendanceRepository.save(currentRecord);
                attendanceCache.setLastRecordDate(LocalDate.now());

                attendanceCacheRepository.save(attendanceCache);
            }
        }
    }

    @Override
    public void checkingOut(AttendanceRecordDTO checkOut) {
        Optional<User> userOpt = userRepository.findById(checkOut.getUserId());
        Optional<AttendanceCache> attendanceCacheOpt = attendanceCacheRepository.findByUser_UserId(checkOut.getUserId());
        if (userOpt.isPresent() && attendanceCacheOpt.isPresent()) {
            AttendanceCache attendanceCache = attendanceCacheOpt.get();
            if (attendanceCache.isCheckIn() && attendanceCache.getLastRecordDate().isEqual(LocalDate.now())) { //if previous record is not check out
                AttendanceRecord currentRecord = attendanceRepository.getOne(attendanceCache.getLastRecordId());
                currentRecord.setCheckOutTimestamp(LocalTime.now());

                attendanceRepository.save(currentRecord);

                attendanceCache.setCheckIn(false);
                attendanceCacheRepository.save(attendanceCache);
            }
        }
    }

    @Override
    public AttendanceInfo getAttendanceInfo(int userId, int month, int year) {
        DateInfo[] dateInfoArray = new DateInfo[32];
        DateOfMonth parsed = ConversionUtils.constructLocalDate(month, year);

        if (LocalDate.now().getMonthValue() < month) {
            return new AttendanceInfo();
        }

        for (int i = 1; i <= parsed.getEndDate().getDayOfMonth(); i++) {
            DateInfo initial = new DateInfo();
            initial.setDate(LocalDate.of(year, month, i));
            dateInfoArray[i] = initial;
        }

        double totalAttendanceInHours = 0;
        int totalLateTime = 0;
        int totalAbsentDay = 0;
        int totalApprovedAbsent = 0;
        int totalUnapprovedAbsent = 0;

        //Add holiday to respective element in array
        List<Holiday> holidaysInMonth = holidayRepository.findByDateBetween(parsed.getStartDate(), parsed.getEndDate());

        for (Holiday record : holidaysInMonth) {
            int dayOfMonth = record.getDate().getDayOfMonth();
            DateInfo assignedDate = dateInfoArray[dayOfMonth];
            assignedDate.setHoliday(true);
        }

        //Add attendance info to respective element in array
        List<AttendanceRecord> attendanceRecordOfMonth = attendanceRepository
                .findALlByUser_UserIdAndDateBetween(userId, parsed.getStartDate(), parsed.getEndDate());

        for (AttendanceRecord record : attendanceRecordOfMonth) {
            int dayOfMonth = record.getDate().getDayOfMonth();
            DateInfo respectiveDate = dateInfoArray[dayOfMonth];

            double difference = getDifferenceInHours(record.getCheckInTimestamp(), record.getCheckOutTimestamp());

            respectiveDate.addTotalHours(difference); //add total check in time

            if (respectiveDate.getFirstCheckIn() == null) { //set first check in timestamp
                respectiveDate.setFirstCheckIn(record.getCheckInTimestamp());
            }
        }

        //Add absent info to respective element in array
        List<FormRecord> absentRequestList = formRecordRepository.findAllByUser_UserIdAndFormTypeAndDateBetween(userId, FormType.ABSENT, parsed.getStartDate(), parsed.getEndDate());
        for (FormRecord record : absentRequestList) {
            int dayOfMonth = record.getDate().getDayOfMonth();
            dateInfoArray[dayOfMonth].setHaveAbsent(true);
            dateInfoArray[dayOfMonth].setAbsentApproved(record.getStatus().equals(FormStatus.ACCEPTED));
        }

        //set total day to calculate, based on current date
        int upperLimit = LocalDate.now().getMonthValue() > parsed.getEndDate().getMonthValue() ? parsed.getEndDate().getDayOfMonth() : LocalDate.now().getDayOfMonth();

        for (int i = 1; i <= upperLimit; i++) {
            DateInfo current = dateInfoArray[i];

            if (!isWeekendOrHoliday(current)) {
                totalAttendanceInHours += current.getTotalInHours();

                if (current.getTotalInHours() < 4) { // is absent if total check in time less than 4 hours and is not a holiday or weekend
                    totalAbsentDay++;

                    if (current.isAbsentApproved()) {
                        totalApprovedAbsent++;
                    } else {
                        totalUnapprovedAbsent++;
                    }

                }

                if (current.getFirstCheckIn() != null && current.getFirstCheckIn().isAfter(LocalTime.of(8, 15))) {
                    totalLateTime++;
                }
            }
        }

        return AttendanceInfo.builder()
                .totalCheckInTimeInHours(totalAttendanceInHours)
                .year(year)
                .month(month)
                .totalLateDays(totalLateTime)
                .totalAbsentDays(totalAbsentDay)
                .totalApprovedAbsentDays(totalApprovedAbsent)
                .totalUnapprovedAbsentDays(totalUnapprovedAbsent)
                .build();
    }

    @Override
    public List<AttendanceRecordDTO> getAttendanceRecords(int userId, int month, int year) {
        DateOfMonth parsed = ConversionUtils.constructLocalDate(month, year);
        List<AttendanceRecord> result = attendanceRepository.findALlByUser_UserIdAndDateBetween(
                userId,
                parsed.getStartDate(),
                parsed.getEndDate());

        return result.stream().map(AttendanceRecordDTO::new).sorted(Comparator.comparing(AttendanceRecordDTO::getId)).collect(Collectors.toList());
    }

    private CustomUserDetails getUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private double getDifferenceInHours(LocalTime checkIn, LocalTime checkOut) {
        long elapsedTime = Duration.between(checkIn, checkOut).toSeconds();

        long subtractBreakTime;

        // minus break time
        if (checkIn.compareTo(LocalTime.of(12, 0)) <= 0) {
            if (checkOut.compareTo(LocalTime.of(12, 0)) <= 0) {
                subtractBreakTime = 0;
            } else if (checkOut.compareTo(LocalTime.of(13, 0)) >= 0) {
                subtractBreakTime = Duration.ofHours(1).toSeconds();
            } else {
                subtractBreakTime = Duration.between(LocalTime.of(12, 0), checkOut).toSeconds();
            }
        } else if (checkIn.compareTo(LocalTime.of(13, 0)) >= 0) {
            subtractBreakTime = 0;
        } else {
            subtractBreakTime = Duration.between(LocalTime.of(12, 0), checkIn).toSeconds();
        }

        return (elapsedTime - subtractBreakTime) / 3600.0; //convert to hours
    }

    private boolean isWeekendOrHoliday(DateInfo record) {
        return record.isHoliday() || record.getDate().getDayOfWeek().equals(DayOfWeek.SATURDAY) || record.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }
}
