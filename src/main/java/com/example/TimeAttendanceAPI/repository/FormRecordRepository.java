package com.example.TimeAttendanceAPI.repository;

import com.example.TimeAttendanceAPI.model.FormRecord;
import com.example.TimeAttendanceAPI.model._enum.FormStatus;
import com.example.TimeAttendanceAPI.model._enum.FormType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FormRecordRepository extends JpaRepository<FormRecord, Integer> {

    List<FormRecord> findAllByUser_UserIdAndDateBetween(int userId, LocalDate fromDate, LocalDate toDate);

    List<FormRecord> findALlByUser_UserIdAndStatusAndDateBetween(int userId, FormStatus status, LocalDate fromDate, LocalDate toDate);

    Page<FormRecord> findAllByUser_UserId(int userId, Pageable pageable);

    Page<FormRecord> findAllByManager_UserId(int userId, Pageable pageable);

    List<FormRecord> findAllByManager_UserIdAndDateBetween(int managerId, LocalDate fromDate, LocalDate toDate);
}
