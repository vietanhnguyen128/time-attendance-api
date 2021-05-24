package com.example.TimeAttendanceAPI.controller.holiday;

import com.example.TimeAttendanceAPI.dto.HolidayDTO;
import com.example.TimeAttendanceAPI.model.Holiday;
import com.example.TimeAttendanceAPI.service.holiday.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HolidayController {
    private final HolidayService holidayService;

    @PostMapping("/holiday")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createHoliday(@RequestBody HolidayDTO request) {
        return new ResponseEntity<>(holidayService.createHoliday(request), HttpStatus.OK);
    }

    @GetMapping("/holiday/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getHoliday(@PathVariable("id") long holidayId) {
        return new ResponseEntity<>(holidayService.getHoliday(holidayId), HttpStatus.OK);
    }

    @GetMapping("/holiday")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getHolidayList(@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                            @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
                                            @RequestParam(name = "sortBy", defaultValue = "") String sortBy) {
        return new ResponseEntity<>(holidayService.getHolidayList(pageNo, pageSize, sortBy), HttpStatus.OK);
    }

    @PutMapping("/holiday/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateHoliday(@PathVariable("id") long id, @RequestBody HolidayDTO request) {
        return new ResponseEntity<>(holidayService.updateHoliday(request), HttpStatus.OK);
    }

    @DeleteMapping("/holiday/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteHoliday(@PathVariable("id") long holidayId) {
        holidayService.deleteHoliday(holidayId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
