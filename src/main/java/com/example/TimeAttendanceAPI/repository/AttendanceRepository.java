package com.example.TimeAttendanceAPI.repository;

import com.example.TimeAttendanceAPI.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    @Query("Select a from Attendance a where a.employeeId = ?1 " +
            "and (a.dateRecord between ?2 and ?3)")
    ArrayList<Attendance> getAttendanceDetailByPeriod(Integer employeeId, LocalDate startDate, LocalDate endDate);

    @Query("Select a from Attendance a where a.employeeId = ?1 and a.dateRecord = ?2")
    ArrayList<Attendance> getAttendanceDetailByDay(Integer employeeId, LocalDate date);

    @Query("Select a from Attendance a where a.employeeId = ?1")
    ArrayList<Attendance> getAttendanceDetail(Integer employeeId);
}