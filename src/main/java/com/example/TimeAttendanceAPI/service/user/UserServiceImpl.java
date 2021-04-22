package com.example.TimeAttendanceAPI.service.user;

import com.example.TimeAttendanceAPI.dto.UserDTO;
import com.example.TimeAttendanceAPI.model.User;
import com.example.TimeAttendanceAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO request) {
        Optional<User> checkUsername = userRepository.findById(request.getUserId());
        if (checkUsername.isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        return null;
    }

    @Override
    public UserDTO updateUserInfo(UserDTO request) {
        return null;
    }
}
