package com.example.TimeAttendanceAPI.dto;

import com.example.TimeAttendanceAPI.model.Holiday;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayDTO {
    private long holidayId;

    private LocalDate date;

    private String holidayDescription;

    public HolidayDTO(Holiday data) {
        this.holidayId = data.getHolidayId();
        this.date = data.getDate();
        this.holidayDescription = data.getHolidayDescription();
    }
}
