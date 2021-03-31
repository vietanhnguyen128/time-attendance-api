package com.example.TimeAttendanceAPI.repository;

import com.example.TimeAttendanceAPI.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {
}
