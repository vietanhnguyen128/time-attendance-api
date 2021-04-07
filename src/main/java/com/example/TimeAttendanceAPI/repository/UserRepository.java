package com.example.TimeAttendanceAPI.repository;

import com.example.TimeAttendanceAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByToken(String token);
}
