package com.example.TimeAttendanceAPI.service.user;

import com.example.TimeAttendanceAPI.dto.UserDTO;
import com.example.TimeAttendanceAPI.dto.UserInfoDTO;

public interface UserService {
    UserInfoDTO updateUserInfoAdmin(Integer userId, UserInfoDTO request);
    UserInfoDTO updateUserInfo(UserInfoDTO request);
    UserInfoDTO getUserInfo(Integer userId);
}
