package com.example.TimeAttendanceAPI.security.service;

import com.example.TimeAttendanceAPI.dto.UserDTO;
import com.example.TimeAttendanceAPI.security.JwtResponse;
import com.example.TimeAttendanceAPI.security.RegisterRequest;

public interface AuthenticationService {
    boolean createUser(RegisterRequest registerRequest);

    JwtResponse login(UserDTO loginRequest);
}
