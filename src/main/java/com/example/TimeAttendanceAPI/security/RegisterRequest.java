package com.example.TimeAttendanceAPI.security;

import com.example.TimeAttendanceAPI.model._enum.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String role;
}
