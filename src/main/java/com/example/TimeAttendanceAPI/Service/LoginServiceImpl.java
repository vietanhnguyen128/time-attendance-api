package com.example.TimeAttendanceAPI.Service;

import com.example.TimeAttendanceAPI.Model.Employee;
import com.example.TimeAttendanceAPI.Repository.EmployeeRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    EmployeeRepository employeeRepository;

    private String decodeHeader(String header) {
        String credential = header.substring("Basic".length()).trim();
        String decoded = new String(Base64.getDecoder().decode(credential), StandardCharsets.UTF_8);
        return decoded;
    }

    @Override
    public boolean checkLogin(String header) {
        boolean status = false;

        if (header != null && header.toLowerCase().startsWith("basic")) {
            String decoded = decodeHeader(header);
            int separatorIndex = decoded.indexOf(":");

            String username = decoded.substring(0, separatorIndex);
            String password = decoded.substring(separatorIndex + 1);

            Employee result = employeeRepository.findByUsername(username);
            if (result != null) {
                status = new BCryptPasswordEncoder().matches(password, result.getPassword());
                result.setToken(DigestUtils.sha256Hex(username + password + LocalDateTime.now().toString()));
                employeeRepository.save(result);
            }
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
    public String getToken(String header) {
        String decoded = decodeHeader(header);
        int separatorIndex = decoded.indexOf(":");
        String username = decoded.substring(0, separatorIndex);

        return employeeRepository.findByUsername(username).getToken();
    }
}
