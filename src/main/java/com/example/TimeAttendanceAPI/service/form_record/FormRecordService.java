package com.example.TimeAttendanceAPI.service.form_record;

import com.example.TimeAttendanceAPI.dto.FormRecordDTO;

import java.util.List;

public interface FormRecordService {
    FormRecordDTO createFormRecord(FormRecordDTO FormRecordDTO);

    List<FormRecordDTO> getFormRecordList();

    FormRecordDTO getSingleFormRecord(String FormRecordId);

    FormRecordDTO updateFormRecord(FormRecordDTO FormRecordDTO);

    void deleteFormRecord(String FormRecordId);
}
