package com.example.TimeAttendanceAPI.Repository;

import com.example.TimeAttendanceAPI.Model.FormRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRecordRepository extends JpaRepository<FormRecord, Integer> {
}
