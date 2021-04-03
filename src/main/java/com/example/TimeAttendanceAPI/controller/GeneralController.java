package com.example.TimeAttendanceAPI.controller;

import com.example.TimeAttendanceAPI.model.Attendance;
import com.example.TimeAttendanceAPI.model.FormRecord;
import com.example.TimeAttendanceAPI.service.GeneralService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class GeneralController {

    private final GeneralService generalService;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private ResponseEntity<String> timeResponse(Duration input) {
        if (input != null)
            return new ResponseEntity<>(String.format("%02d:%02d:%02d", input.toHours(), input.toMinutesPart(), input.toSecondsPart()), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Create attendance record", description = "Create attendance record")
    @ApiResponse(responseCode = "200", description = "Success")
    @PostMapping("/attendance")
    public ResponseEntity<Attendance> createAttendanceRecord(@RequestBody @Valid Attendance record) {
        return new ResponseEntity<>(generalService.createAttendanceRecord(record), HttpStatus.OK);
    }

    @Operation(summary = "Create form record", description = "Create form record")
    @ApiResponse(responseCode = "200", description = "Success")
    @PostMapping("/form")
    public ResponseEntity<FormRecord> createForm(@RequestBody @Valid FormRecord form) {
        return new ResponseEntity<>(generalService.createForm(form), HttpStatus.OK);
    }

    //Attendance methods
    @Operation(summary = "Get total attendance time", description = "Get total attendance time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Employee not found")})
    @GetMapping("/attendance/{id}")
    public ResponseEntity<String> getTotalAttendanceTime(@PathVariable("id") Integer id) {
        Duration result = generalService.getTotalAttendanceTime(id);
        if (result != null)
            return timeResponse(result);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get attendance time by day", description = "Get attendance time by day")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Employee not found")})
    @GetMapping(value = "/attendance/{id}", params = "date")
    public ResponseEntity<String> getAttendanceTimeByDay(@PathVariable("id") Integer id, @Param("date") String date) {
        Duration result = generalService.getAttendanceTimeByDay(id, date);
        if (result != null)
            return timeResponse(result);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get attendance time by period", description = "Get attendance time by period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Employee not found")})
    @GetMapping(value = "/attendance/{id}", params = {"start", "end"})
    public ResponseEntity<String> getAttendanceTimeByPeriod(@PathVariable("id") Integer id, @Param("start") String startDate, @Param("end") String endDate) {
        Duration result = generalService.getAttendanceTimeByPeriod(id, startDate, endDate);
        if (result != null)
            return timeResponse(result);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Other methods
    @Operation(summary = "Get total time by type", description = "Get total time by type. There are 4 types: late time, working time, absent time, overtime")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Employee not found")})
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

        if (result != null)
            return timeResponse(result);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get total time by type and period", description = "Get total time by type and period. There are 4 types: late time, working time, absent time, overtime")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Employee not found")})
    @GetMapping(value = "/info/{id}", params = {"type", "start", "end"})
    public ResponseEntity<String> getTotalTimeByTypeAndPeriod(@PathVariable("id") Integer employeeId, @Param("type") String type,
                                                              @Param("start") String start, @Param("end") String end) {

        Duration result;
        switch (type) {
            case "late":
                result = generalService.getLateTimeByPeriod(employeeId, start, end);
                break;
            case "working":
                result = generalService.getWorkingTimeByPeriod(employeeId, start, end);
                break;
            case "absent":
                result = generalService.getAbsentTimeByPeriod(employeeId, start, end);
                break;
            case "overtime":
                result = generalService.getOvertimeByPeriod(employeeId, start, end);
                break;
            default:
                return new ResponseEntity<>("Invalid type", HttpStatus.BAD_REQUEST);
        }

        if (result != null)
            return timeResponse(result);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
