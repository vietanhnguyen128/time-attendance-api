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

    private String name;

    private int age;

    private String gender;

    private int role;

    private String managerId;

    private String departmentId;

    private Shift shiftType;

    public UserDTO(User user) {

    }
}
