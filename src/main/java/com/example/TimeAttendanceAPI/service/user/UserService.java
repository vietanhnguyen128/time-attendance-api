package com.example.TimeAttendanceAPI.service.user;

import com.example.TimeAttendanceAPI.dto.PagedResponse;
import com.example.TimeAttendanceAPI.dto.UserInfoDTO;

public interface UserService {
    UserInfoDTO updateUserInfoAdmin(UserInfoDTO request);
    UserInfoDTO updateUserInfo(UserInfoDTO request);
    UserInfoDTO getUserInfo(Integer userId);
    PagedResponse getUserList(int pageNo, int pageSize, String sortBy, String role);
}
