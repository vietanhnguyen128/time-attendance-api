package com.example.TimeAttendanceAPI.Controller;

import com.example.TimeAttendanceAPI.Model.Attendance;
import com.example.TimeAttendanceAPI.Model.FormRecord;
import com.example.TimeAttendanceAPI.Service.GeneralServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class GeneralController {

    @Autowired
    private GeneralServiceImpl generalService;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private ResponseEntity<String> timeResponse(Duration input) {
        if (input != null)
            return new ResponseEntity<>(String.format("%02d:%02d:%02d", input.toHours(), input.toMinutesPart(), input.toSecondsPart()), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/attendance")
    public ResponseEntity<Attendance> createAttendanceRecord(@RequestBody @Valid Attendance record) {
        return new ResponseEntity<>(generalService.createAttendanceRecord(record), HttpStatus.OK);
    }

    @PostMapping("/form")
    public ResponseEntity<FormRecord> createForm(@RequestBody @Valid FormRecord form) {
        return new ResponseEntity<>(generalService.createForm(form), HttpStatus.OK);
    }

    //Attendance methods
    @GetMapping("/attendance/{id}")
    public ResponseEntity<String> getTotalAttendanceTime(@PathVariable("id") Integer id) {
        Duration result = generalService.getTotalAttendanceTime(id);
        return timeResponse(result);
    }

    @GetMapping(value = "/attendance/{id}", params = "date")
    public ResponseEntity<String> getAttendanceTimeByDay(@PathVariable("id") Integer id, @Param("date") String date) {
        LocalDate convertedDate = LocalDate.parse(date, dateTimeFormatter);
        Duration result = generalService.getAttendanceTimeByDay(id, convertedDate);
        return timeResponse(result);
    }

    @GetMapping(value = "/attendance/{id}", params = {"start", "end"})
    public ResponseEntity<String> getAttendanceTimeByPeriod(@PathVariable("id") Integer id, @Param("date") String startDate, @Param("end") String endDate) {
        LocalDate convertedStartDate = LocalDate.parse(startDate, dateTimeFormatter);
        LocalDate convertedEndDate = LocalDate.parse(endDate, dateTimeFormatter);
        Duration result = generalService.getAttendanceTimeByPeriod(id, convertedStartDate, convertedEndDate);
        return timeResponse(result);
    }

    //Other methods
    @GetMapping(value = "/info/{id}", params = {"type"})
    public ResponseEntity<String> getTotalTimeByType(@PathVariable("id") Integer employeeId, @Param("type") String type) {
        Duration result;
        switch (type) {
            case "late":
                 result = generalService.getTotalLateTime(employeeId);
                 break;
            case "working":
                result = generalService.getTotalWorkingTime(employeeId);
                break;
            case "absent":
                result = generalService.getTotalAbsentTime(employeeId);
                break;
            case "overtime":
                result = generalService.getTotalOvertime(employeeId);
                break;
            default:
                return new ResponseEntity<>("Invalid type", HttpStatus.BAD_REQUEST);
        }

        return timeResponse(result);
    }

    @GetMapping(value = "/info/{id}", params = {"type", "start", "end"})
    public ResponseEntity<String> getTotalTimeByTypeAndPeriod(@PathVariable("id") Integer employeeId, @Param("type") String type,
                                                              @Param("start") String start, @Param("end") String end) {

        LocalDate convertedStart = LocalDate.parse(start, dateTimeFormatter);
        LocalDate convertedEnd = LocalDate.parse(end, dateTimeFormatter);

        Duration result;
        switch (type) {
            case "late":
                result = generalService.getLateTimeByPeriod(employeeId, convertedStart, convertedEnd);
                break;
            case "working":
                result = generalService.getWorkingTimeByPeriod(employeeId, convertedStart, convertedEnd);
                break;
            case "absent":
                result = generalService.getAbsentTimeByPeriod(employeeId, convertedStart, convertedEnd);
                break;
            case "overtime":
                result = generalService.getOvertimeByPeriod(employeeId, convertedStart, convertedEnd);
                break;
            default:
                return new ResponseEntity<>("Invalid type", HttpStatus.BAD_REQUEST);
        }

        return timeResponse(result);
    }
}
