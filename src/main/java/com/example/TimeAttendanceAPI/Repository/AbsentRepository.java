package com.example.TimeAttendanceAPI.Repository;

import com.example.TimeAttendanceAPI.Model.Absent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.List;

@Repository
public interface AbsentRepository extends JpaRepository<Absent, Integer> {

    @Query("select a from Absent a where a.employeeId = ?1 and month(a.dateOfAbsent) = ?2")
    List<Absent> getTotalAbsentTime(Integer employeeId, Integer month);
}
