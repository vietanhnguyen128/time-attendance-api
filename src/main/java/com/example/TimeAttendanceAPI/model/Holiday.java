package com.example.TimeAttendanceAPI.model;

import com.example.TimeAttendanceAPI.dto.HolidayDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long holidayId;

    private LocalDate date;

    private String holidayName;

    public Holiday(HolidayDTO request) {
        this.date = request.getDate();
        this.holidayName = request.getHolidayName();
    }
}
