package com.example.TimeAttendanceAPI.service.user;

import com.example.TimeAttendanceAPI.dto.PagedResponse;
import com.example.TimeAttendanceAPI.dto.UserDTO;
import com.example.TimeAttendanceAPI.dto.UserInfoDTO;
import com.example.TimeAttendanceAPI.model.User;
import com.example.TimeAttendanceAPI.model._enum.ERole;
import com.example.TimeAttendanceAPI.repository.UserRepository;
import com.example.TimeAttendanceAPI.utils.PageableUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void updateUserInfoAdmin(UserInfoDTO request) {

    }

    @Override
    public void updateUserInfo(UserInfoDTO request) {
        Optional<User> userOpt = userRepository.findById(request.getUserId());
        if (userOpt.isPresent()) {
            User entity = userOpt.get();
            entity.updatePersonalInfo(request);
            userRepository.save(entity);
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
}
