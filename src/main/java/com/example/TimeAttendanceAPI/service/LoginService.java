package com.example.TimeAttendanceAPI.service;

public interface LoginService {
    boolean checkLogin(String header);

    boolean validateToken(String token);

    String getToken(String username);
}
