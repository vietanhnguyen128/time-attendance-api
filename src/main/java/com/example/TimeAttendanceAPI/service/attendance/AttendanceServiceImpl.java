package com.example.TimeAttendanceAPI.service.attendance;

import com.example.TimeAttendanceAPI.dto.AttendanceRecordDTO;
import com.example.TimeAttendanceAPI.model.AttendanceRecord;
import com.example.TimeAttendanceAPI.repository.AttendanceRepository;
import com.example.TimeAttendanceAPI.utils.PageableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;

    @Override
    public Page<AttendanceRecordDTO> getAttendanceRecordList(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageableUtils.createPageable(pageNo, pageSize, sortBy);
        return attendanceRepository.findAll(pageable).map(AttendanceRecordDTO::new);
    }

    @Override
    public Page<AttendanceRecordDTO> getAttendanceRecordListOfMonth(int pageNo, int pageSize, String sortBy, LocalDate date) {
        Pageable pageable = PageableUtils.createPageable(pageNo, pageSize, sortBy);
        return attendanceRepository.findAll(pageable).map(AttendanceRecordDTO::new);
    }

    @Override
    public AttendanceRecordDTO updateAttendanceRecord(AttendanceRecordDTO AttendanceRecordDTO) {
        return null;
    }

    @Override
    public void deleteAttendanceRecord(String AttendanceRecordId) {

    }
}
