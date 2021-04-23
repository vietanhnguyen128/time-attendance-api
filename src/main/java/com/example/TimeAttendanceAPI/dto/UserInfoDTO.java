package com.example.TimeAttendanceAPI.dto;

import com.example.TimeAttendanceAPI.model.User;
import com.example.TimeAttendanceAPI.model._enum.Gender;
import com.example.TimeAttendanceAPI.model._enum.Shift;

public class UserInfoDTO {
    private String name;

    private int age;

    private Gender gender;

    private String role;

    private String managerId;

    private String managerName;

    private int departmentId;

    private String departmentName;

    private Shift shiftType;

    public UserInfoDTO(User user) {
        this.name = user.getName();
        this.age = user.getAge();
        this.gender = user.getGender();
        this.role = user.getRole().getRoleName();
        this.managerId = user.getManager().getUserId();
        this.managerName = user.getManager().getName();
        this.departmentId = user.getDepartment().getDepartmentId();
        this.departmentName = user.getDepartment().getDepartmentName();
        this.shiftType = user.getShiftType();
    }
}
