package com.example.TimeAttendanceAPI.Repository;

import com.example.TimeAttendanceAPI.Model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Integer> {
}
