package com.example.TimeAttendanceAPI;

import com.example.TimeAttendanceAPI.controller.GeneralController;
import com.example.TimeAttendanceAPI.interceptor.RequestInterceptor;
import com.example.TimeAttendanceAPI.model.Attendance;
import com.example.TimeAttendanceAPI.model.FormRecord;
import com.example.TimeAttendanceAPI.service.GeneralServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @BeforeEach
    public void setup() throws Exception {
        when(requestInterceptor.preHandle(any(),any(),any())).thenReturn(true);
        mapper = new ObjectMapper();
    }

    @AfterEach
    public void teardown() throws Exception {

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
        FormRecord formRecord = new FormRecord(1, 1, dateVal, timeVal, "absent", "pending");
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
        when(generalService.getTotalAttendanceTime(anyInt())).thenReturn(Duration.ofSeconds(3600));

        this.mockMvc.perform(get("/attendance/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void getTotalAttendanceFailed() throws Exception {
        when(generalService.getTotalAttendanceTime(anyInt())).thenReturn(null);

        this.mockMvc.perform(get("/attendance/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getTotalAttendanceByDaySuccess() throws Exception {
        when(generalService.getAttendanceTimeByDay(anyInt(), anyString())).thenReturn(Duration.ofSeconds(3600));

        this.mockMvc.perform(get("/attendance/{id}", 1)
            .param("date", LocalDate.now().toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getTotalAttendanceByDayFailed() throws Exception {
        when(generalService.getAttendanceTimeByDay(anyInt(), anyString())).thenReturn(null);

        this.mockMvc.perform(get("/attendance/{id}", 1)
                .param("date", LocalDate.now().toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getTotalAttendanceByPeriodSuccess() throws Exception {
        when(generalService.getAttendanceTimeByPeriod(anyInt(), anyString(), anyString())).thenReturn(Duration.ofSeconds(3600));

        this.mockMvc.perform(get("/attendance/{id}", 1)
                .param("start", LocalDate.now().toString())
                .param("end", LocalDate.now().toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getTotalAttendanceByPeriodFailed() throws Exception {
        when(generalService.getAttendanceTimeByPeriod(anyInt(), any(), any())).thenReturn(null);

        System.out.println(generalService.getAttendanceTimeByPeriod(anyInt(), anyString(), anyString()));

        this.mockMvc.perform(get("/attendance/{id}", 1)
                .param("start", LocalDate.now().toString())
                .param("end", LocalDate.now().toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
