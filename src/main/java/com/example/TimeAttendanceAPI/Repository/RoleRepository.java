package com.example.TimeAttendanceAPI.Repository;

import com.example.TimeAttendanceAPI.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Integer> {
}
