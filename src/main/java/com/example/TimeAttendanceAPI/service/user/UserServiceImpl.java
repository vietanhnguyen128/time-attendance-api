package com.example.TimeAttendanceAPI.service.user;

import com.example.TimeAttendanceAPI.dto.UserDTO;
import com.example.TimeAttendanceAPI.dto.UserInfoDTO;
import com.example.TimeAttendanceAPI.model.User;
import com.example.TimeAttendanceAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserInfoDTO updateUserInfoAdmin(Integer userId, UserInfoDTO request) {
        return new UserInfoDTO();
    }

    @Override
    public UserInfoDTO updateUserInfo(UserInfoDTO request) {
        return null;
    }

    @Override
    public UserInfoDTO getUserInfo(Integer userId) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        return new UserInfoDTO(userOpt.get());
    }
}
