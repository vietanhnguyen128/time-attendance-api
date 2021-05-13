package com.example.TimeAttendanceAPI.repository;

import com.example.TimeAttendanceAPI.model.FormRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FormRecordRepository extends JpaRepository<FormRecord, Integer> {
    List<FormRecord> findALlByUser_UserIdAndDateBetween(int userId, LocalDate fromDate, LocalDate toDate);

    List<FormRecord> findAllByManager_UserIdAndDateBetween(int managerId, LocalDate fromDate, LocalDate toDate);
}
