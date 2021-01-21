package com.example.TimeAttendanceAPI;

import com.example.TimeAttendanceAPI.Controller.AdminController;
import com.example.TimeAttendanceAPI.Interceptor.RequestInterceptor;
import com.example.TimeAttendanceAPI.Service.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestInterceptor requestInterceptor;

    @MockBean
    private AdminServiceImpl adminService;
}
