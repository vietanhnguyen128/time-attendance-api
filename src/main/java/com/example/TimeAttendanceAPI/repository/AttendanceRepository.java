package com.example.TimeAttendanceAPI.repository;

import com.example.TimeAttendanceAPI.model.AttendanceRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceRecord, Integer> {


    Page<AttendanceRecord> findAllByIdAndDate(int userId, LocalDate date, Pageable pageable);
}
