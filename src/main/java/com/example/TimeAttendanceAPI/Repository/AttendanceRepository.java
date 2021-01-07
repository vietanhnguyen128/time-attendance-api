package com.example.TimeAttendanceAPI.Repository;

import com.example.TimeAttendanceAPI.Model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<AttendanceRepository, Integer> {

    List<Attendance> findByIdAndMonth(Integer id, Month month);
    Attendance findByIdAndDay(Integer id, LocalDate date);
}
