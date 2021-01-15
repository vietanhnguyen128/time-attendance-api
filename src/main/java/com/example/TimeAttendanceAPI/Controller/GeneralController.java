package com.example.TimeAttendanceAPI.Controller;

import com.example.TimeAttendanceAPI.Model.Attendance;
import com.example.TimeAttendanceAPI.Model.FormRecord;
import com.example.TimeAttendanceAPI.Service.GeneralServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Duration;

@RestController
public class GeneralController {

    @Autowired
    private GeneralServiceImpl generalService;

    @PostMapping("/attendance")
    public ResponseEntity<Attendance> createAttendanceRecord(@RequestBody @Valid Attendance record) {
        return new ResponseEntity<>(generalService.createAttendanceRecord(record), HttpStatus.OK);
    }

    @PostMapping("/form")
    public ResponseEntity<FormRecord> createForm(@RequestBody @Valid FormRecord form) {
        return new ResponseEntity<>(generalService.createForm(form), HttpStatus.OK);
    }

    //Get
    @GetMapping("/attendance")
    public ResponseEntity<Duration> getTotalAttendanceTime(@Param("id") Integer id) {
        Duration result = generalService.getTotalAttendanceTime(id);
        if (result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
