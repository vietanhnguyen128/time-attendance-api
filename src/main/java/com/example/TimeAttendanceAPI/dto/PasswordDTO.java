package com.example.TimeAttendanceAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDTO {
    Integer userId;

    String oldPassword;

    String newPassword;
}
