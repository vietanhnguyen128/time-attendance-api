package com.example.TimeAttendanceAPI.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private int userId;

    private String name;

    private int age;

    private String gender;

    private String managerId;

    private String departmentId;

    private String shift;

    @JsonIgnore
    private String username;

    @JsonIgnore
    private String password;
}
