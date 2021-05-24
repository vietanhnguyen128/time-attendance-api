package com.example.TimeAttendanceAPI.dto;

import com.example.TimeAttendanceAPI.model.Holiday;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayDTO {
    private long holidayId;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    private String holidayDescription;

    public HolidayDTO(Holiday data) {
        this.holidayId = data.getHolidayId();
        this.date = data.getDate();
        this.holidayDescription = data.getHolidayDescription();
    }
}
