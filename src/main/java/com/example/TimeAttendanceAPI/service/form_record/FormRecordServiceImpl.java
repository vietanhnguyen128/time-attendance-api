package com.example.TimeAttendanceAPI.service.form_record;

import com.example.TimeAttendanceAPI.dto.FormRecordDTO;
import com.example.TimeAttendanceAPI.model.DateOfMonth;
import com.example.TimeAttendanceAPI.model.FormRecord;
import com.example.TimeAttendanceAPI.model.User;
import com.example.TimeAttendanceAPI.model._enum.FormStatus;
import com.example.TimeAttendanceAPI.repository.FormRecordRepository;
import com.example.TimeAttendanceAPI.repository.UserRepository;
import com.example.TimeAttendanceAPI.utils.ConversionUtils;
import com.example.TimeAttendanceAPI.utils.PageableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE"})
public class FormRecordServiceImpl implements FormRecordService {
    private final UserRepository userRepository;
    private final FormRecordRepository formRecordRepository;

    @Override
    public FormRecordDTO createFormRecord(FormRecordDTO request) {
        Optional<User> employee = userRepository.findById(request.getUserId());
        if (employee.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        FormRecord toCreate = new FormRecord(request);
        toCreate.setUser(employee.get());
        toCreate.setManager(employee.get().getManager());

        return new FormRecordDTO(formRecordRepository.save(toCreate));
    }

    @Override
    public Page<FormRecordDTO> getFormRecordList(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageableUtils.createPageable(pageNo, pageSize, sortBy);
        return formRecordRepository.findAll(pageable).map(FormRecordDTO::new);
    }

    @Override
    public FormRecordDTO getSingleFormRecord(Integer formId) {
        Optional<FormRecord> result = formRecordRepository.findById(formId);
        if (result.isEmpty()) {
            throw new RuntimeException("Form not found!");
        }
        return new FormRecordDTO(result.get());

    }

    @Override
    public FormRecordDTO updateFormRecord(FormRecordDTO request) {
        Optional<FormRecord> result = formRecordRepository.findById(request.getId());
        if (result.isEmpty()) {
            throw new RuntimeException("Form not found!");
        }

        FormRecord toUpdate = result.get();
        if (toUpdate.getStatus().equals(FormStatus.PENDING)) {
            toUpdate.updateFromDTO(request);
            return new FormRecordDTO(formRecordRepository.save(toUpdate));
        }

        throw new RuntimeException("Can not modify!");
    }

    @Override
    public FormRecordDTO processFormRecord(FormRecordDTO request) {
        Optional<FormRecord> result = formRecordRepository.findById(request.getId());
        if (result.isEmpty()) {
            throw new RuntimeException("Form not found!");
        }

        FormRecord toUpdate = result.get();
        toUpdate.setStatus(request.getStatus());

        return new FormRecordDTO(formRecordRepository.save(toUpdate));
    }

    @Override
    public void deleteFormRecord(Integer formId) {
        formRecordRepository.deleteById(formId);
    }

    @Override
    public int getFormOfTypeOfStatusOfMonth(int userId, String formType, String status, int month, int year) {
        DateOfMonth converted = ConversionUtils.constructLocalDate(month, year);
        List<FormRecord> retrievedList = formRecordRepository
                .findALlByUser_UserIdAndFormTypeAndStatusAndDateBetween(userId, formType, status, converted.getStartDate(), converted.getEndDate());
        return retrievedList.size();
    }
}
