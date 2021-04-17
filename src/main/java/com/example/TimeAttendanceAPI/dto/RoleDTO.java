package com.example.TimeAttendanceAPI.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleDTO {
    private int id;

    private String roleName;

    private String description;
}
