package com.example.TimeAttendanceAPI.repository;

import com.example.TimeAttendanceAPI.model.AttendanceCache;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceCacheRepository extends JpaRepository<AttendanceCache, Integer> {
    Optional<AttendanceCache> findByUserId(Integer userId);
}
