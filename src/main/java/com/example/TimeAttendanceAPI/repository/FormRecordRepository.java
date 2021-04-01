package com.example.TimeAttendanceAPI.repository;

import com.example.TimeAttendanceAPI.model.FormRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public interface FormRecordRepository extends JpaRepository<FormRecord, Integer> {
    @Query("Select f from Employee e, FormRecord f where e.managerId = ?1 and e.employeeId = f.employeeId")
    ArrayList<FormRecord> getSubordinatesFormRecords(Integer adminId);

    @Query("Select f from Employee e, FormRecord f where e.employeeId = ?1 " +
            "and e.employeeId = f.employeeId " +
            "and f.status = 'accept' " +
            "and f.formType = ?2")
    ArrayList<FormRecord> getApprovedForms(Integer employeeId, String formType);

    @Query("Select f from Employee e, FormRecord f where e.employeeId = ?1 " +
            "and e.employeeId = f.employeeId " +
            "and f.status = 'accept' " +
            "and f.formType = ?2 " +
            "and (f.day between ?3 and ?4)")
    ArrayList<FormRecord> getApprovedFormsByPeriod(Integer employeeId, String formType, LocalDate startTime, LocalDate endTime);
}
