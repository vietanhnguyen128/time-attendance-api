package com.example.TimeAttendanceAPI.repository;

import com.example.TimeAttendanceAPI.model.FormRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRecordRepository extends JpaRepository<FormRecord, Integer> {
}
