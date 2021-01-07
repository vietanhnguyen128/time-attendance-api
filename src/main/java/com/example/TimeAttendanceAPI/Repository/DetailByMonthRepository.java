package com.example.TimeAttendanceAPI.Repository;

import com.example.TimeAttendanceAPI.Model.DetailByMonth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Month;
import java.util.Optional;

public interface DetailByMonthRepository extends JpaRepository<DetailByMonth, Integer> {
    DetailByMonth findByEmployeeIdAndMonth(Integer id, Month month);
}
