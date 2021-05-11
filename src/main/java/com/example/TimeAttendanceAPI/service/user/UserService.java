package com.example.TimeAttendanceAPI.service.user;

import com.example.TimeAttendanceAPI.dto.UserDTO;
import com.example.TimeAttendanceAPI.dto.UserInfoDTO;

public interface UserService {
    void updateUserInfoAdmin(UserInfoDTO request);
    void updateUserInfo(UserInfoDTO request);
    UserInfoDTO getUserInfo(Integer userId);
}
