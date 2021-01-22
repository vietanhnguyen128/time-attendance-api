package com.example.TimeAttendanceAPI;

import com.example.TimeAttendanceAPI.Controller.LoginController;
import com.example.TimeAttendanceAPI.Interceptor.RequestInterceptor;
import com.example.TimeAttendanceAPI.Repository.EmployeeRepository;
import com.example.TimeAttendanceAPI.Service.LoginServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LoginServiceImpl loginService;

    @MockBean
    RequestInterceptor requestInterceptor;

    private String decodeHeader(String header) {
        String credential = header.substring("Basic".length()).trim();
        String decoded = new String(Base64.getDecoder().decode(credential), StandardCharsets.UTF_8);
        return decoded;
    }

    @Test
    public void loginSuccess() throws Exception {
        when(requestInterceptor.preHandle(any(), any(), any())).thenReturn(true);
        when(loginService.checkLogin(anyString())).thenReturn(true);

        this.mockMvc.perform(post("/login")
                .header("Authorization", "Basic blablabla"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void loginFailed() throws Exception {
        when(requestInterceptor.preHandle(any(), any(), any())).thenReturn(true);
        when(loginService.checkLogin(anyString())).thenReturn(false);

        this.mockMvc.perform(post("/login")
                .header("Authorization", "Basic blablabla"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
