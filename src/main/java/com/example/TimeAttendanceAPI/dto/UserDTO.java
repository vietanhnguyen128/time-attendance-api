package com.example.TimeAttendanceAPI.dto;

import com.example.TimeAttendanceAPI.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private String username;

    private String password;

    public UserDTO(User request) {
        this.username = request.getUsername();
        this.password = request.getPassword();
    }
}
