package com.example.TimeAttendanceAPI.service.department;

import com.example.TimeAttendanceAPI.dto.DepartmentDTO;
import com.example.TimeAttendanceAPI.dto.PagedResponse;
import com.example.TimeAttendanceAPI.model.Department;
import com.example.TimeAttendanceAPI.model.User;
import com.example.TimeAttendanceAPI.repository.DepartmentRepository;
import com.example.TimeAttendanceAPI.repository.UserRepository;
import com.example.TimeAttendanceAPI.utils.PageableUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department input = new Department(departmentDTO);

        if (departmentRepository.findByDepartmentName(departmentDTO.getDepartmentName()).isPresent()) {
            throw new RuntimeException("Department name already exists!");
        }

        if (departmentDTO.getManagerId() != null) {
            Optional<User> managerOpt = userRepository.findById(departmentDTO.getManagerId());
            if (managerOpt.isPresent()) {
                if (managerOpt.get().getManagedDepartments() == null) {
                    input.setManager(managerOpt.get());
                } else {
                    throw new RuntimeException("Manager has been assigned to another department");
                }
            } else {
                throw new RuntimeException("Manager not found");
            }
        }

        return new DepartmentDTO(departmentRepository.save(input));
    }

    @Override
    public PagedResponse getDepartmentList(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageableUtils.createPageable(pageNo, pageSize, sortBy);
        Page<DepartmentDTO> result = departmentRepository.findAll(pageable).map(DepartmentDTO::new);
        return PagedResponse.builder()
                .totalItems(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .currentPage(result.getPageable().getPageNumber())
                .data(result.getContent())
                .build();
    }

    @Override
    public DepartmentDTO getSingleDepartment(Integer departmentId) {
        Optional<Department> result = departmentRepository.findById(departmentId);
        if (result.isEmpty()) {
            throw new RuntimeException("Department not found!");
        }
        return new DepartmentDTO(result.get());
    }

    @Override
    public DepartmentDTO updateDepartment(DepartmentDTO departmentDTO) {
        Optional<Department> result = departmentRepository.findById(departmentDTO.getDepartmentId());
        if (result.isEmpty()) {
            throw new RuntimeException("Department not found!");
        }

        Optional<User> managerOpt = userRepository.findById(departmentDTO.getManagerId());
        if (managerOpt.isEmpty()) {
            throw new RuntimeException("Manager not found!");
        }

        Department toUpdate = result.get();
        toUpdate.setManager(managerOpt.get());

        return new DepartmentDTO(departmentRepository.save(toUpdate));
    }

    @Override
    public void deleteDepartment(Integer departmentId) {
        departmentRepository.deleteById(departmentId);
    }
}
