package com.example.TimeAttendanceAPI.model._enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public enum Constant {
    TIMESTAMP_START(LocalTime.of(8, 0)),
    TIMESTAMP_END(LocalTime.of(17, 0)),
    TIMESTAMP_BREAK_START(LocalTime.of(12, 0)),
    TIMESTAMP_BREAK_END(LocalTime.of(13, 0));

    private final LocalTime timestamp;
}
