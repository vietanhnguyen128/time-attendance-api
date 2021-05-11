package com.example.TimeAttendanceAPI.service.attendance;

import com.example.TimeAttendanceAPI.dto.AttendanceRecordDTO;
import com.example.TimeAttendanceAPI.model.AttendanceCache;
import com.example.TimeAttendanceAPI.model.AttendanceRecord;
import com.example.TimeAttendanceAPI.model.User;
import com.example.TimeAttendanceAPI.repository.AttendanceCacheRepository;
import com.example.TimeAttendanceAPI.repository.AttendanceRepository;
import com.example.TimeAttendanceAPI.repository.UserRepository;
import com.example.TimeAttendanceAPI.security.service.CustomUserDetails;
import com.example.TimeAttendanceAPI.utils.PageableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE"})
public class AttendanceServiceImpl implements AttendanceService {
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceCacheRepository attendanceCacheRepository;

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

    private CustomUserDetails getUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
