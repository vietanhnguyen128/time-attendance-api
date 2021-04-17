package com.example.TimeAttendanceAPI.model._enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public enum Shift {
    FULL_TIME(LocalTime.of(8, 0), LocalTime.of(17, 0),
            LocalTime.of(12, 0), LocalTime.of(13, 0)),
    PART_TIME_MORNING(LocalTime.of(8,0), LocalTime.of(12,0), null, null),
    PART_TIME_AFTERNOON(LocalTime.of(13,0), LocalTime.of(17,0), null, null);

    private final LocalTime startTime;
    private final LocalTime endTime;
    private final LocalTime breakStart;
    private final LocalTime breakEnd;
}
