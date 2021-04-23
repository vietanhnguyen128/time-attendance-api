package com.example.TimeAttendanceAPI.dto;

import com.example.TimeAttendanceAPI.model.User;
import com.example.TimeAttendanceAPI.model._enum.Shift;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private String userId;

    private String password;

    public UserDTO(User request) {
        this.userId = request.getUserId();
        this.password = request.getPassword();
    }
}
