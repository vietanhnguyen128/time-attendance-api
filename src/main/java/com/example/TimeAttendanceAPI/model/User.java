package com.example.TimeAttendanceAPI.model;

import com.example.TimeAttendanceAPI.dto.UserDTO;
import com.example.TimeAttendanceAPI.dto.UserInfoDTO;
import com.example.TimeAttendanceAPI.model._enum.Gender;
import com.example.TimeAttendanceAPI.model._enum.Shift;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String name;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @Enumerated(EnumType.STRING)
    private Shift shiftType;

    private String username;

    private String password;

    private String email;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<User> managedUsers;

    @OneToOne(mappedBy = "manager", fetch = FetchType.LAZY)
    private Department managedDepartments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<AttendanceRecord> attendanceRecordList;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<FormRecord> managedForms;

    public User(UserDTO request) {
        this.username = request.getUsername();
        this.password = request.getPassword();
    }

    public void updatePersonalInfo(UserInfoDTO userInfoDTO) {
        this.name = userInfoDTO.getName();
        this.age = userInfoDTO.getAge();
        this.gender = userInfoDTO.getGender();
        this.email = userInfoDTO.getEmail();
    }
}
