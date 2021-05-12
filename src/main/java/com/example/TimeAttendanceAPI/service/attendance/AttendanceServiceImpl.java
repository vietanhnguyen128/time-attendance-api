package com.example.TimeAttendanceAPI.service.attendance;

import com.example.TimeAttendanceAPI.dto.AttendanceInfo;
import com.example.TimeAttendanceAPI.dto.AttendanceRecordDTO;
import com.example.TimeAttendanceAPI.model.AttendanceCache;
import com.example.TimeAttendanceAPI.model.AttendanceRecord;
import com.example.TimeAttendanceAPI.model.User;
import com.example.TimeAttendanceAPI.repository.AttendanceCacheRepository;
import com.example.TimeAttendanceAPI.repository.AttendanceRepository;
import com.example.TimeAttendanceAPI.repository.UserRepository;
import com.example.TimeAttendanceAPI.security.service.CustomUserDetails;
import com.example.TimeAttendanceAPI.service.form_record.FormRecordService;
import com.example.TimeAttendanceAPI.utils.PageableUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE"})
public class AttendanceServiceImpl implements AttendanceService {
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceCacheRepository attendanceCacheRepository;
    private final FormRecordService formRecordService;

    @Override
    public Page<AttendanceRecordDTO> getAttendanceRecordList(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageableUtils.createPageable(pageNo, pageSize, sortBy);
        return attendanceRepository.findAll(pageable).map(AttendanceRecordDTO::new);
    }

    @Override
    public Page<AttendanceRecordDTO> getAttendanceRecordListOfMonth(int pageNo, int pageSize, String sortBy, LocalDate date) {
        Pageable pageable = PageableUtils.createPageable(pageNo, pageSize, sortBy);
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return attendanceRepository.findAllByIdAndDate(userDetails.getId(), date, pageable).map(AttendanceRecordDTO::new);
    }

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
                        .timestamp(LocalTime.now())
                        .isCheckIn(true)
                        .build();

                attendanceRepository.save(checkInRecord);

                attendanceCache.setCheckIn(true);
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
            if (attendanceCache.isCheckIn()) { //if previous record is not check out
                AttendanceRecord checkOutRecord = AttendanceRecord.builder()
                        .user(userOpt.get())
                        .date(LocalDate.now())
                        .timestamp(LocalTime.now())
                        .isCheckIn(false)
                        .build();

                attendanceRepository.save(checkOutRecord);

                attendanceCache.setCheckIn(false);
                attendanceCacheRepository.save(attendanceCache);
            }
        }
    }

    @Override
    public AttendanceInfo getAttendanceInfo(int userId, int month, int year) {
        AttendanceInfo result = new AttendanceInfo();
        long totalAttendanceInMinutes = 0;

        DateOfMonth parsed = constructLocalDate(month, year);
        List<AttendanceRecord> attendanceRecordOfMonth = attendanceRepository
                .findALlByUser_UserIdAndDateBetween(userId, parsed.getFromDate(), parsed.getToDate());

        List<AttendanceRecord> checkInRecords = attendanceRecordOfMonth.stream().filter(AttendanceRecord::isCheckIn).collect(Collectors.toList());
        List<AttendanceRecord> checkOutRecords = ListUtils.subtract(attendanceRecordOfMonth, checkInRecords);

        Queue<AttendanceRecord> checkInQueue = new LinkedList<>(checkInRecords);
        Queue<AttendanceRecord> checkOutQueue = new LinkedList<>(checkOutRecords);

        while (!checkInQueue.isEmpty()) {
            AttendanceRecord checkIn = checkInQueue.poll();
            AttendanceRecord checkOut = checkOutQueue.peek();

            if (checkIn.getDate().getDayOfMonth() == checkOut.getDate().getDayOfMonth()) {
                totalAttendanceInMinutes+= getDifference(checkIn.getTimestamp(), checkOut.getTimestamp());
            }
        }

        return result;
    }

    @Override
    public List<AttendanceRecordDTO> getAttendanceRecords(int userId, int month, int year) {
        DateOfMonth parsed = constructLocalDate(month, year);
        List<AttendanceRecord> result = attendanceRepository.findALlByUser_UserIdAndDateBetween(
                userId,
                parsed.getFromDate(),
                parsed.getToDate());

        return result.stream().map(AttendanceRecordDTO::new).collect(Collectors.toList());
    }

    private CustomUserDetails getUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private DateOfMonth constructLocalDate(int month, int year) {
        String startDate = "01/";
        String endDate;

        if (month < 10) {
            startDate = startDate + "0" + month + "/" + year;
        } else {
            startDate = startDate + month + "/" + year;
        }

        LocalDate convertedDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        int endOfMonth = convertedDate.withDayOfMonth(convertedDate.getMonth().length(convertedDate.isLeapYear())).getDayOfMonth();

        if (endOfMonth < 10) {
            endDate = "0" + endOfMonth + startDate.substring(2);
        } else {
            endDate = endOfMonth + startDate.substring(2);
        }

        return new DateOfMonth(startDate, endDate);
    }

    private long getDifference(LocalTime checkIn, LocalTime checkOut) {
        long elapsedMinutes = Duration.between(checkOut, checkIn).toMinutes();

        long subtractBreakTime;

        if (checkOut.compareTo(LocalTime.of(12, 0)) <= 0) {
            subtractBreakTime = 0;
        } else if (checkOut.compareTo(LocalTime.of(13, 0)) >= 0) {
            subtractBreakTime = 60;
        } else {
            subtractBreakTime = Duration.between(checkOut, LocalTime.of(12, 0)).toMinutes();
        }

        return elapsedMinutes - subtractBreakTime;
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class DateOfMonth {
    private LocalDate fromDate;

    private LocalDate toDate;

    public DateOfMonth(String startDate, String endDate) {
        fromDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        toDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
