package com.example.TimeAttendanceAPI.repository;

import com.example.TimeAttendanceAPI.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRoleRepository  extends JpaRepository<Role, Integer> {
}
