package com.example.TimeAttendanceAPI.repository;

import com.example.TimeAttendanceAPI.model.Role;
import com.example.TimeAttendanceAPI.model._enum.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
