package com.example.TimeAttendanceAPI.dto;

import com.example.TimeAttendanceAPI.model.User;
import com.example.TimeAttendanceAPI.model._enum.ERole;
import com.example.TimeAttendanceAPI.model._enum.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private int userId;

    private String name;

    private int age;

    private Gender gender;

    private ERole role;

    private Integer managerId;

    private String managerName;

    private int departmentId;

    private String departmentName;

    @Email
    private String email;

    public UserInfoDTO(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.age = user.getAge();
        this.gender = user.getGender();
        this.role = user.getRole().getName();
        this.managerId = user.getManager().getUserId() == null ? 0 : user.getManager().getUserId();
        this.managerName = user.getManager().getName() == null ? "" : user.getManager().getName();
        this.departmentId = user.getDepartment().getDepartmentId() == null ? 0 : user.getDepartment().getDepartmentId();
        this.departmentName = user.getDepartment().getDepartmentName() == null ? "" : user.getDepartment().getDepartmentName();
        this.email = user.getEmail() == null ? "" : user.getEmail();
    }
}
