package com.example.TimeAttendanceAPI.service.holiday;

import com.example.TimeAttendanceAPI.dto.HolidayDTO;
import com.example.TimeAttendanceAPI.dto.PagedResponse;
import com.example.TimeAttendanceAPI.model.Holiday;
import com.example.TimeAttendanceAPI.repository.HolidayRepository;
import com.example.TimeAttendanceAPI.utils.PageableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HolidayServiceImpl implements HolidayService {
    private final HolidayRepository holidayRepository;

    @Override
    public HolidayDTO createHoliday(HolidayDTO data) {
        if (holidayRepository.existsByDate(data.getDate())) {
            throw new RuntimeException("Holiday on this date is already existed.");
        }

        Holiday savedEntity = holidayRepository.save(new Holiday(data));
        return new HolidayDTO(savedEntity);
    }

    @Override
    public HolidayDTO updateHoliday(HolidayDTO data) {
        Optional<Holiday> holidayOpt = holidayRepository.findById(data.getHolidayId());
        if (holidayOpt.isEmpty()) {
            throw new RuntimeException("Holiday not found.");
        }

        Holiday value = holidayOpt.get();

        if (!value.getDate().isEqual(data.getDate())) {
            if (holidayRepository.existsByDate(data.getDate())) {
                throw new RuntimeException("Holiday on updated date is already existed.");
            }
        }

        value.setDate(data.getDate());
        value.setHolidayDescription(data.getHolidayDescription());
        return new HolidayDTO(holidayRepository.save(value));
    }

    @Override
    public HolidayDTO getHoliday(long holidayId) {
        Optional<Holiday> holidayOpt = holidayRepository.findById(holidayId);
        if (holidayOpt.isEmpty()) {
            throw new RuntimeException("Holiday not found.");
        }

        return new HolidayDTO(holidayOpt.get());
    }

    @Override
    public PagedResponse getHolidayList(int pageNo, int pageSize, String sortBy) {
        Page<HolidayDTO> holidayList;
        Pageable pageable = PageableUtils.createPageable(pageNo, pageSize, sortBy);

        holidayList = holidayRepository.findAll(pageable).map(HolidayDTO::new);

        return PagedResponse.builder()
                .totalPages(holidayList.getTotalPages())
                .totalItems(holidayList.getTotalElements())
                .currentPage(holidayList.getPageable().getPageNumber())
                .data(holidayList.getContent())
                .build();
    }

    @Override
    public void deleteHoliday(long holidayId) {
        holidayRepository.deleteById(holidayId);
    }
}
