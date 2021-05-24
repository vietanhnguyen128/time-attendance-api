package com.example.TimeAttendanceAPI.service.holiday;

import com.example.TimeAttendanceAPI.dto.HolidayDTO;
import com.example.TimeAttendanceAPI.dto.PagedResponse;

import java.util.List;

public interface HolidayService {
    HolidayDTO createHoliday(HolidayDTO data);

    HolidayDTO updateHoliday(HolidayDTO data);

    HolidayDTO getHoliday(long holidayId);

    PagedResponse getHolidayList(int pageNo, int pageSize, String sortBy);

    void deleteHoliday(long holidayId);
}
