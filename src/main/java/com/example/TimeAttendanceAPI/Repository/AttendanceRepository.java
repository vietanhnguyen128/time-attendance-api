package com.example.TimeAttendanceAPI.Repository;

import com.example.TimeAttendanceAPI.Model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    @Query("Select (a.dateRecord, a.timeRecord) from Employee e, Attendance a where e.employeeId = ?1 " +
            "and e.employeeId = a.employeeId " +
            "and month(a.dateRecord) = ?2")
    ArrayList<Attendance> getAttendanceDetailByMonth(Integer employeeId, Integer month);

    @Query("Select (a.dateRecord, a.timeRecord) from Employee e, Attendance a where e.employeeId = ?1 " +
            "and e.employeeId = a.employeeId")
    ArrayList<Attendance> getAttendanceDetail(Integer employeeId);
}
