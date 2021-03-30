package com.example.TimeAttendanceAPI.Service;

public interface LoginService {
    abstract boolean checkLogin(String header);

    abstract boolean validateToken(String token);

    abstract String getToken(String username);
}
