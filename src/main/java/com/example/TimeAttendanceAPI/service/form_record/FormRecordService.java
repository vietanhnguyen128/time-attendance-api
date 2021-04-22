package com.example.TimeAttendanceAPI.service.form_record;

import com.example.TimeAttendanceAPI.dto.FormRecordDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FormRecordService {
    FormRecordDTO createFormRecord(FormRecordDTO request);

    Page<FormRecordDTO> getFormRecordList(int pageNo, int pageSize, String sortBy);

    FormRecordDTO getSingleFormRecord(Integer formId);

    FormRecordDTO updateFormRecord(FormRecordDTO request);

    FormRecordDTO processFormRecord(FormRecordDTO request);

    void deleteFormRecord(Integer formId);
}
