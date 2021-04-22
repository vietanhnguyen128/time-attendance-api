package com.example.TimeAttendanceAPI.service.form_record;

import com.example.TimeAttendanceAPI.dto.FormRecordDTO;
import com.example.TimeAttendanceAPI.model.FormRecord;
import com.example.TimeAttendanceAPI.model.User;
import com.example.TimeAttendanceAPI.repository.FormRecordRepository;
import com.example.TimeAttendanceAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
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
        toUpdate.updateFromDTO(request);

        return new FormRecordDTO(formRecordRepository.save(toUpdate));
    }

    @Override
    public void deleteFormRecord(Integer formId) {
        formRecordRepository.deleteById(formId);
    }
}
