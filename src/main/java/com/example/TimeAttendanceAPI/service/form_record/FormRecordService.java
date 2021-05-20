package com.example.TimeAttendanceAPI.service.form_record;

import com.example.TimeAttendanceAPI.dto.FormRecordDTO;
import com.example.TimeAttendanceAPI.dto.PagedResponse;
import com.example.TimeAttendanceAPI.model.FormRecord;
import com.example.TimeAttendanceAPI.model._enum.FormStatus;
import com.example.TimeAttendanceAPI.model._enum.FormType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FormRecordService {
    FormRecordDTO createFormRecord(FormRecordDTO request);

    PagedResponse getFormRecordList(int pageNo, int pageSize, String sortBy, String formType);

    FormRecordDTO getSingleFormRecord(Integer formId);

    FormRecordDTO updateFormRecord(FormRecordDTO request);

    FormRecordDTO processFormRecord(FormRecordDTO request);

    int getFormOfTypeOfStatusOfMonth(int userId, FormType formType, FormStatus status, int month, int year);

    void deleteFormRecord(Integer formId);
}
