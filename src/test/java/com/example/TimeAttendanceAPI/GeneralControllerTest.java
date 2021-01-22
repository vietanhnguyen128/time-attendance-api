package com.example.TimeAttendanceAPI;

import com.example.TimeAttendanceAPI.Controller.GeneralController;
import com.example.TimeAttendanceAPI.Interceptor.RequestInterceptor;
import com.example.TimeAttendanceAPI.Model.Attendance;
import com.example.TimeAttendanceAPI.Model.FormRecord;
import com.example.TimeAttendanceAPI.Repository.EmployeeRepository;
import com.example.TimeAttendanceAPI.Service.GeneralServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GeneralController.class)
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GeneralControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RequestInterceptor requestInterceptor;

    @MockBean
    GeneralServiceImpl generalService;

    ObjectMapper mapper;

    DateTimeFormatter formatter;

    @BeforeAll
    public void setup() throws Exception {
        when(requestInterceptor.preHandle(any(),any(),any())).thenReturn(true);
        mapper = new ObjectMapper();
    }

    @Test
    public void createAttendanceSuccess() throws Exception {
        LocalDate dateVal = LocalDate.now();
        LocalTime timeVal = LocalTime.now();
        Attendance attendance = new Attendance(1, 1, dateVal, timeVal);
        when(generalService.createAttendanceRecord(any())).thenReturn(attendance);

        System.out.println(mapper.writeValueAsString(attendance));
        String temp = mapper.writeValueAsString(attendance);

        this.mockMvc.perform(post("/attendance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(attendance)))
                .andExpect(status().isOk());
    }

    @Test
    public void createFormRecordSuccess() throws Exception {
        LocalDate dateVal = LocalDate.now();
        LocalTime timeVal = LocalTime.now();
        FormRecord formRecord = new FormRecord(1, 1, dateVal, timeVal, "absent", "pending", LocalDateTime.now(), null, null);
        when(generalService.createForm(any())).thenReturn(formRecord);

        String temp = mapper.writeValueAsString(formRecord);
        System.out.println(mapper.writeValueAsString(formRecord));

        this.mockMvc.perform(post("/form")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(formRecord)))
                .andExpect(status().isOk());
    }

    @Test
    public void getTotalAttendanceSuccess() throws Exception {
        String total = Duration.ofSeconds(3600).toString();

        when(generalService.getTotalAttendanceTime(anyInt())).thenReturn(Duration.ofSeconds(3600));

        this.mockMvc.perform(get("/attendance/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void getTotalAttendanceFailed() throws Exception {
        String total = Duration.ofSeconds(3600).toString();

        when(generalService.getTotalAttendanceTime(anyInt())).thenReturn(null);

        this.mockMvc.perform(get("/attendance/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getTotalAttendanceByDaySuccess() throws Exception {
        String total = Duration.ofSeconds(3600).toString();

        when(generalService.getAttendanceTimeByDay(anyInt(), anyString())).thenReturn(Duration.ofSeconds(3600));

        this.mockMvc.perform(get("/attendance/{id}", 1)
            .param("date", LocalDate.now().toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getTotalAttendanceByDayFailed() throws Exception {
        String total = Duration.ofSeconds(3600).toString();

        when(generalService.getAttendanceTimeByDay(anyInt(), anyString())).thenReturn(null);

        this.mockMvc.perform(get("/attendance/{id}", 1)
                .param("date", LocalDate.now().toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getTotalAttendanceByPeriodSuccess() throws Exception {
        String total = Duration.ofSeconds(3600).toString();

        when(generalService.getAttendanceTimeByPeriod(anyInt(), anyString(), anyString())).thenReturn(Duration.ofSeconds(3600));

        this.mockMvc.perform(get("/attendance/{id}", 1)
                .param("startDate", LocalDate.now().toString())
                .param("endDate", LocalDate.now().toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getTotalAttendanceByPeriodFailed() throws Exception {
        String total = Duration.ofSeconds(3600).toString();

        when(generalService.getAttendanceTimeByPeriod(anyInt(), anyString(), anyString())).thenReturn(null);

        this.mockMvc.perform(get("/attendance/{id}", 1)
                .param("startDate", LocalDate.now().toString())
                .param("endDate", LocalDate.now().toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
