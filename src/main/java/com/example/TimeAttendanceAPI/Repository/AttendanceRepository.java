package com.example.TimeAttendanceAPI.Repository;

import com.example.TimeAttendanceAPI.Model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    @Query("select a from Attendance a where a.employeeId = ?1 and month(a.dateOfWork) = ?2")
    List<Attendance> findByIdAndMonth(Integer id, Integer month);

    Attendance findByIdAndDateOfWork(Integer id, LocalDate date);
}
