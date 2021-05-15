package com.example.TimeAttendanceAPI.service.user;

import com.example.TimeAttendanceAPI.dto.UserDTO;
import com.example.TimeAttendanceAPI.dto.UserInfoDTO;
import com.example.TimeAttendanceAPI.model.User;
import com.example.TimeAttendanceAPI.model._enum.ERole;
import com.example.TimeAttendanceAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<UserInfoDTO> getUserList(String role) {
        List<User> userList;

        if (StringUtils.isNotEmpty(role)) {
            userList = userRepository.findAllByRole(ERole.valueOf(role));
        } else {
            userList = userRepository.findAll();
        }

        return userList.stream().map(UserInfoDTO::new).collect(Collectors.toList());
    }
}
