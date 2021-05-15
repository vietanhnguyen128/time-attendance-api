package com.example.TimeAttendanceAPI.repository;

import com.example.TimeAttendanceAPI.model.Role;
import com.example.TimeAttendanceAPI.model.User;
import com.example.TimeAttendanceAPI.model._enum.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    List<User> findAllByRole(ERole role);
}
