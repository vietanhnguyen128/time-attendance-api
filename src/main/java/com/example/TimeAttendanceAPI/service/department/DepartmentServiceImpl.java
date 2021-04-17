package com.example.TimeAttendanceAPI.service.department;

import com.example.TimeAttendanceAPI.dto.DepartmentDTO;
import com.example.TimeAttendanceAPI.model.Department;
import com.example.TimeAttendanceAPI.model.User;
import com.example.TimeAttendanceAPI.repository.DepartmentRepository;
import com.example.TimeAttendanceAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
            Optional<User> manager = userRepository.findById(departmentDTO.getManagerId());
            if (manager.isPresent()) {
                input.setManager(manager.get());
            } else {
                throw new RuntimeException("Deparment not found");
            }
        }

        return new DepartmentDTO(departmentRepository.save(input));
    }

    @Override
    public List<DepartmentDTO> getDepartmentList() {
        List<DepartmentDTO> departmentDTOList = departmentRepository.findAll()
                .stream().map(DepartmentDTO::new).collect(Collectors.toList());
        return null;
    }

    @Override
    public DepartmentDTO getSingleDepartment(String departmentId) {
        return null;
    }

    @Override
    public DepartmentDTO updateDepartment(DepartmentDTO departmentDTO) {
        return null;
    }

    @Override
    public void deleteDepartment(String departmentId) {

    }
}
