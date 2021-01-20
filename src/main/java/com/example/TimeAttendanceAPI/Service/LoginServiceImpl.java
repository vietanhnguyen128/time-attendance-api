package com.example.TimeAttendanceAPI.Service;

import com.example.TimeAttendanceAPI.Model.Employee;
import com.example.TimeAttendanceAPI.Repository.EmployeeRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public boolean checkLogin(String username, String password) {
        Employee result = employeeRepository.findByUsername(username);
        boolean status = false;
        if (result != null) {
            status = new BCryptPasswordEncoder().matches(password, result.getPassword());
            result.setToken(DigestUtils.sha256Hex(username + password + LocalDateTime.now().toString()));
        }
        return status;
    }

    @Override
    public boolean validateToken(String token) {
        Employee result = employeeRepository.findByToken(token);
        boolean status = false;
        if (result != null) {
            status = token.equals(result.getToken());
        }
        return status;
    }

    @Override
    public String getToken(String username) {
        return employeeRepository.findByUsername(username).getToken();
    }
}
