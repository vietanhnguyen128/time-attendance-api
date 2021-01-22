package com.example.TimeAttendanceAPI.Service;

import com.example.TimeAttendanceAPI.Model.Employee;

import java.util.Optional;

public interface LoginService {
    abstract boolean checkLogin(String header);

    abstract boolean validateToken(String token);

    abstract String getToken(String username);
}
