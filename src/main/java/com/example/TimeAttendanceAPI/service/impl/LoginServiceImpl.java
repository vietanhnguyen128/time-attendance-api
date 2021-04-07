package com.example.TimeAttendanceAPI.service.impl;

import com.example.TimeAttendanceAPI.model.Employee;
import com.example.TimeAttendanceAPI.model.User;
import com.example.TimeAttendanceAPI.repository.UserRepository;
import com.example.TimeAttendanceAPI.service.LoginService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserRepository userRepository;

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

            User result = userRepository.findByUsername(username);
            if (result != null) {
                status = new BCryptPasswordEncoder().matches(password, result.getPassword());
                //result.setToken(DigestUtils.sha256Hex(username + password + LocalDateTime.now().toString()));
                userRepository.save(result);
            }
        }
        return status;
    }

    @Override
    public boolean validateToken(String token) {
        User result = userRepository.findByToken(token);
        boolean status = false;
        if (result != null) {
            //status = token.equals(result.getToken());
        }
        return status;
    }

//    @Override
//    public String getToken(String header) {
//        String decoded = decodeHeader(header);
//        int separatorIndex = decoded.indexOf(":");
//        String username = decoded.substring(0, separatorIndex);
//
//        return userRepository.findByUsername(username).getToken();
//    }
}
