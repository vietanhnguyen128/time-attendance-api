package com.example.TimeAttendanceAPI.service.holiday;

import com.example.TimeAttendanceAPI.dto.HolidayDTO;

import java.util.List;

public interface HolidayService {
    HolidayDTO createHoliday(HolidayDTO data);

    HolidayDTO updateHoliday(HolidayDTO data);

    HolidayDTO getHoliday(long holidayId);

    List<HolidayDTO> getHolidayList();

    void deleteHoliday(long holidayId);
}
