package com.example.TimeAttendanceAPI.Controller;

import com.example.TimeAttendanceAPI.Model.Employee;
import com.example.TimeAttendanceAPI.Service.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Locale;

@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    LoginServiceImpl loginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String header) {

        if (header != null && header.toLowerCase().startsWith("basic")) {
            String credential = header.substring("Basic".length()).trim();
            String decoded = new String(Base64.getDecoder().decode(credential), StandardCharsets.UTF_8);
            int separatorIndex = decoded.indexOf(":");

            String username = decoded.substring(0, separatorIndex);
            String password = decoded.substring(separatorIndex + 1);

            boolean result = loginService.checkLogin(username, password);
            if (result == true)
                return new ResponseEntity<>(loginService.getToken(username), HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);
    }
}
