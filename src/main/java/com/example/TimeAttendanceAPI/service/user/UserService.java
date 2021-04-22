package com.example.TimeAttendanceAPI.service.user;

import com.example.TimeAttendanceAPI.dto.UserDTO;

public interface UserService {
    UserDTO createUser(UserDTO request);

    UserDTO updateUserInfo(UserDTO request);
}
