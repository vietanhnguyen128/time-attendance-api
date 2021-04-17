package com.example.TimeAttendanceAPI.model;

import com.example.TimeAttendanceAPI.model._enum.Shift;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    private int userId;

    private String name;

    private int age;

    private String gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    private Shift shiftType;

    private String password;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<User> managedUsers;

    @OneToOne(mappedBy = "manager", fetch = FetchType.LAZY)
    private Department managedDepartments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<AttendanceRecord> attendanceRecordList;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<FormRecord> managedForms;
}
