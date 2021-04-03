package com.example.TimeAttendanceAPI.repository;

import com.example.TimeAttendanceAPI.model.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceRecord, Integer> {

    @Query("Select a from AttendanceRecord a where a.employeeId = ?1 " +
            "and (a.dateRecord between ?2 and ?3)")
    ArrayList<AttendanceRecord> getAttendanceDetailByPeriod(Integer employeeId, LocalDate startDate, LocalDate endDate);

    @Query("Select a from AttendanceRecord a where a.employeeId = ?1 and a.dateRecord = ?2")
    ArrayList<AttendanceRecord> getAttendanceDetailByDay(Integer employeeId, LocalDate date);

    @Query("Select a from AttendanceRecord a where a.employeeId = ?1")
    ArrayList<AttendanceRecord> getAttendanceDetail(Integer employeeId);
}
