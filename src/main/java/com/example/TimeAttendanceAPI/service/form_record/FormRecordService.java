package com.example.TimeAttendanceAPI.service.form_record;

import com.example.TimeAttendanceAPI.dto.FormRecordDTO;

import java.util.List;

public interface FormRecordService {
    FormRecordDTO createFormRecord(FormRecordDTO request);

    List<FormRecordDTO> getFormRecordList();

    FormRecordDTO getSingleFormRecord(Integer formId);

    FormRecordDTO updateFormRecord(FormRecordDTO request);

    void deleteFormRecord(Integer formId);
}
