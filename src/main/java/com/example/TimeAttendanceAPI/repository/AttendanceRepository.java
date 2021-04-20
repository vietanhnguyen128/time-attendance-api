package com.example.TimeAttendanceAPI.repository;

import com.example.TimeAttendanceAPI.model.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceRecord, Integer> {

}
