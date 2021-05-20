package com.example.TimeAttendanceAPI.service.user;

import com.example.TimeAttendanceAPI.dto.PagedResponse;
import com.example.TimeAttendanceAPI.dto.UserDTO;
import com.example.TimeAttendanceAPI.dto.UserInfoDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    void updateUserInfoAdmin(UserInfoDTO request);
    void updateUserInfo(UserInfoDTO request);
    UserInfoDTO getUserInfo(Integer userId);
    PagedResponse getUserList(int pageNo, int pageSize, String sortBy, String role);
}
