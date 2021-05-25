package com.example.TimeAttendanceAPI.dto;

import com.example.TimeAttendanceAPI.model.User;
import com.example.TimeAttendanceAPI.model._enum.ERole;
import com.example.TimeAttendanceAPI.model._enum.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private int userId;

    private String name;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthdate;

    private Gender gender;

    private String role;

    private Integer managerId;

    private String managerName;

    private Integer departmentId;

    private String departmentName;

    @Email
    private String email;

    public UserInfoDTO(User user) {
        this.userId = user.getUserId();
        this.name = user.getName() == null ? "" : user.getName();
        this.birthdate = user.getBirthdate() == null ? null : user.getBirthdate();
        this.gender = user.getGender() == null ? Gender.NOT_SPECIFIED : user.getGender();
        this.role = user.getRole().name();
        this.managerId = user.getManager() == null ? 0 : user.getManager().getUserId();
        this.managerName = user.getManager() == null ? "" : user.getManager().getName();
        this.departmentId = user.getDepartment() == null ? 0 : user.getDepartment().getDepartmentId();
        this.departmentName = user.getDepartment() == null ? "" : user.getDepartment().getDepartmentName();
        this.email = user.getEmail() == null ? "" : user.getEmail();
    }
}
