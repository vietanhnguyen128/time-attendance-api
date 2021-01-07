package com.example.TimeAttendanceAPI.Repository;

import com.example.TimeAttendanceAPI.Model.Absent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsentRepository extends JpaRepository<Absent, Integer> {
}
