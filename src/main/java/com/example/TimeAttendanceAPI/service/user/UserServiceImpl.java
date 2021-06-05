package com.example.TimeAttendanceAPI.service.user;

import com.example.TimeAttendanceAPI.dto.PagedResponse;
import com.example.TimeAttendanceAPI.dto.PasswordDTO;
import com.example.TimeAttendanceAPI.dto.UserInfoDTO;
import com.example.TimeAttendanceAPI.model.Department;
import com.example.TimeAttendanceAPI.model.User;
import com.example.TimeAttendanceAPI.model._enum.ERole;
import com.example.TimeAttendanceAPI.repository.DepartmentRepository;
import com.example.TimeAttendanceAPI.repository.UserRepository;
import com.example.TimeAttendanceAPI.security.service.CustomUserDetails;
import com.example.TimeAttendanceAPI.service.department.DepartmentService;
import com.example.TimeAttendanceAPI.utils.PageableUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserInfoDTO updateUserInfoAdmin(UserInfoDTO request) {
        Optional<User> userOpt = userRepository.findById(request.getUserId());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        } else {
            User entity = userOpt.get();
            entity.updatePersonalInfo(request);

            if (EnumUtils.isValidEnum(ERole.class, request.getRole())) {
                entity.setRole(ERole.valueOf(request.getRole()));
            } else {
                throw new RuntimeException("Role is empty!");
            }

            Optional<User> managerOpt = userRepository.findById(request.getManagerId());
            if (managerOpt.isPresent()) {
                entity.setManager(managerOpt.get());
            } else {
                entity.setManager(null);
            }

            Optional<Department> departmentOpt = departmentRepository.findById(request.getDepartmentId());
            if (departmentOpt.isPresent()) {
                entity.setDepartment(departmentOpt.get());
            } else {
                entity.setDepartment(null);
            }

            return new UserInfoDTO(userRepository.save(entity));
        }
    }

    @Override
    public UserInfoDTO updateUserInfo(UserInfoDTO request) {
        Optional<User> userOpt = userRepository.findById(request.getUserId());
        if (userOpt.isPresent()) {
            User entity = userOpt.get();
            entity.updatePersonalInfo(request);
            return new UserInfoDTO(userRepository.save(entity));
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public UserInfoDTO getUserInfo(Integer userId) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        return new UserInfoDTO(userOpt.get());
    }

    @Override
    public PagedResponse getUserList(int pageNo, int pageSize, String sortBy, String role) {
        Page<UserInfoDTO> userList;

        Pageable pageable = PageableUtils.createPageable(pageNo, pageSize, sortBy);

        if (StringUtils.isNotEmpty(role)) {
            userList = userRepository.findAllByRole(pageable, ERole.valueOf(role)).map(UserInfoDTO::new);
        } else {
            userList = userRepository.findAll(pageable).map(UserInfoDTO::new);
        }

        return PagedResponse.builder()
                .totalPages(userList.getTotalPages())
                .totalItems(userList.getTotalElements())
                .currentPage(userList.getPageable().getPageNumber())
                .data(userList.getContent())
                .build();
    }

    @Override
    public void changePassword(PasswordDTO request) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (passwordEncoder.matches(request.getOldPassword(), userDetails.getPassword())) {
            User user = userRepository.getOne(userDetails.getId());
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new RuntimeException("Old password is not correct!");
        }
    }

    @Override
    public void resetPassword(PasswordDTO request) {
        Optional<User> userOpt = userRepository.findById(request.getUserId());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found.");
        }
        User user = userOpt.get();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
