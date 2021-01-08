package com.example.TimeAttendanceAPI.Repository;

import com.example.TimeAttendanceAPI.Model.DetailByMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.Optional;

@Repository
public interface DetailByMonthRepository extends JpaRepository<DetailByMonth, Integer> {
    DetailByMonth findByEmployeeIdAndMonth(Integer id, Integer month);
}
