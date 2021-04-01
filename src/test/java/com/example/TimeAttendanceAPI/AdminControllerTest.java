package com.example.TimeAttendanceAPI;

import com.example.TimeAttendanceAPI.controller.AdminController;
import com.example.TimeAttendanceAPI.interceptor.RequestInterceptor;
import com.example.TimeAttendanceAPI.model.*;
import com.example.TimeAttendanceAPI.service.impl.AdminServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestInterceptor requestInterceptor;

    @MockBean
    private AdminServiceImpl adminService;

    Employee sampleEmp;
    Department sampleDept;
    Position samplePos;
    AccountRole sampleRole;

    ObjectMapper mapper;

    @BeforeEach
    public void setup() throws Exception {
        when(requestInterceptor.preHandle(any(), any(), any())).thenReturn(true);
        sampleEmp = new Employee();
        sampleDept = new Department();
        samplePos = new Position();
        sampleRole = new AccountRole();
        mapper = new ObjectMapper();
    }

    @Test
    public void createEmployeeSuccess() throws Exception {
        when(adminService.createNewEmployee(any())).thenReturn(new Employee());
        this.mockMvc.perform(post("/employee/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(sampleEmp)))
                .andExpect(status().isOk());
    }

    @Test
    public void createDepartmentSuccess() throws Exception {
        when(adminService.createNewDepartment(any())).thenReturn(new Department());
        this.mockMvc.perform(post("/employee/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(sampleEmp)))
                .andExpect(status().isOk());
    }

    @Test
    public void createPositionSuccess() throws Exception {
        when(adminService.createNewPosition(any())).thenReturn(new Position());
        this.mockMvc.perform(post("/employee/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(sampleEmp)))
                .andExpect(status().isOk());
    }

    @Test
    public void createAccountRoleSuccess() throws Exception {
        when(adminService.createNewRole(any())).thenReturn(new AccountRole());
        this.mockMvc.perform(post("/employee/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(sampleEmp)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateEmployeeSuccess() throws Exception {
        when(adminService.updateEmployee(anyInt(), any())).thenReturn(new Employee());
        this.mockMvc.perform(put("/employee/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(sampleEmp)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateEmployeeFailed() throws Exception {
        when(adminService.updateEmployee(anyInt(), any())).thenReturn(null);
        this.mockMvc.perform(put("/employee/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(sampleEmp)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateDepartmentSuccess() throws Exception {
        when(adminService.updateDepartment(anyInt(), any())).thenReturn(new Department());
        this.mockMvc.perform(put("/department")
                .param("id", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(sampleDept)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateDepartmentFailed() throws Exception {
        when(adminService.updateDepartment(anyInt(), any())).thenReturn(null);
        this.mockMvc.perform(put("/department")
                .param("id", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(sampleDept)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updatePositionSuccess() throws Exception {
        when(adminService.updatePosition(anyInt(), any())).thenReturn(new Position());
        this.mockMvc.perform(put("/position")
                .param("id", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(samplePos)))
                .andExpect(status().isOk());
    }

    @Test
    public void updatePositionFailed() throws Exception {
        when(adminService.updatePosition(anyInt(), any())).thenReturn(null);
        this.mockMvc.perform(put("/position")
                .param("id", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(samplePos)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateRoleSuccess() throws Exception {
        when(adminService.updateRole(anyInt(), any())).thenReturn(new AccountRole());
        this.mockMvc.perform(put("/role")
                .param("id", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(sampleRole)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateRoleFailed() throws Exception {
        when(adminService.updateRole(anyInt(), any())).thenReturn(null);
        this.mockMvc.perform(put("/role")
                .param("id", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(sampleRole)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getEmployeeSuccess() throws Exception {
        when(adminService.getEmployeeById(anyInt())).thenReturn(Optional.ofNullable(new Employee()));
        this.mockMvc.perform(get("/employee/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeeFailed() throws Exception {
        when(adminService.getEmployeeById(anyInt())).thenReturn(Optional.ofNullable(null));
        this.mockMvc.perform(get("/employee/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllEmployeeSuccess() throws Exception {
        when(adminService.getAllEmployee()).thenReturn(new ArrayList<Employee>());
        this.mockMvc.perform(get("/employee"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllDepartmentSuccess() throws Exception {
        when(adminService.getAllDepartment()).thenReturn(new ArrayList<Department>());
        this.mockMvc.perform(get("/department"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllPositionSuccess() throws Exception {
        when(adminService.getAllPosition()).thenReturn(new ArrayList<Position>());
        this.mockMvc.perform(get("/position"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllRoleSuccess() throws Exception {
        when(adminService.getALlRole()).thenReturn(new ArrayList<AccountRole>());
        this.mockMvc.perform(get("/role"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteEmployeeSuccess() throws Exception {
        when(adminService.getEmployeeById(anyInt())).thenReturn(Optional.ofNullable(new Employee()));
        when(adminService.deleteEmployee(anyInt())).thenReturn(true);
        this.mockMvc.perform(delete("/employee/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteEmployeeFailed() throws Exception {
        when(adminService.getEmployeeById(anyInt())).thenReturn(Optional.ofNullable(null));
        when(adminService.deleteEmployee(anyInt())).thenReturn(true);
        this.mockMvc.perform(delete("/employee/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteDepartmentSuccess() throws Exception {
        when(adminService.deleteDepartment(anyInt())).thenReturn(true);
        this.mockMvc.perform(delete("/department")
                .param("id", String.valueOf(1)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteDepartmentFailed() throws Exception {
        when(adminService.deleteDepartment(anyInt())).thenReturn(false);
        this.mockMvc.perform(delete("/department")
                .param("id", String.valueOf(1)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deletePositionSuccess() throws Exception {
        when(adminService.deletePosition(anyInt())).thenReturn(true);
        this.mockMvc.perform(delete("/position")
                .param("id", String.valueOf(1)))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePositionFailed() throws Exception {
        when(adminService.deletePosition(anyInt())).thenReturn(false);
        this.mockMvc.perform(delete("/position")
                .param("id", String.valueOf(1)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteRoleSuccess() throws Exception {
        when(adminService.deleteRole(anyInt())).thenReturn(true);
        this.mockMvc.perform(delete("/role")
                .param("id", String.valueOf(1)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteRoleFailed() throws Exception {
        when(adminService.deleteRole(anyInt())).thenReturn(false);
        this.mockMvc.perform(delete("/role")
                .param("id", String.valueOf(1)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getFormRecordsSuccess() throws Exception {
        when(adminService.getSubordinatesFormRecords(anyInt())).thenReturn(new ArrayList<FormRecord>());
        this.mockMvc.perform(get("/form/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getFormRecordsByTypeSuccess() throws Exception {
        when(adminService.getSubordinatesFormRecordsByType(anyInt(), anyString())).thenReturn(new ArrayList<FormRecord>());
        this.mockMvc.perform(get("/form/1")
                .param("type", "abc"))
                .andExpect(status().isOk());
    }

    @Test
    public void getFormRecordsByStatusSuccess() throws Exception {
        when(adminService.getSubordinatesFormRecordsByStatus(anyInt(), anyString())).thenReturn(new ArrayList<FormRecord>());
        this.mockMvc.perform(get("/form/1")
                .param("status", "abc"))
                .andExpect(status().isOk());
    }

    @Test
    public void formApprovalSuccess() throws Exception {
        when(adminService.formApproval(anyInt(), anyString())).thenReturn(new FormRecord());
        this.mockMvc.perform(put("/form/1")
                .param("formId", String.valueOf(1))
                .param("status", "accept"))
                .andExpect(status().isOk());
    }

    @Test
    public void formApprovalFailed() throws Exception {
        when(adminService.formApproval(anyInt(), anyString())).thenReturn(null);
        this.mockMvc.perform(put("/form/1")
                .param("formId", String.valueOf(1))
                .param("status", "accept"))
                .andExpect(status().isNotFound());
    }
}
