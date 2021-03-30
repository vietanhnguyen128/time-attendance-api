package com.example.TimeAttendanceAPI.Controller;

import com.example.TimeAttendanceAPI.Service.LoginServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    LoginServiceImpl loginService;

    @Operation(summary = "Login", description = "Login using username and password. Return a token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Invalid username or password")})
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String header) {
        boolean result = loginService.checkLogin(header);
        if (result)
            return new ResponseEntity<>(loginService.getToken(header), HttpStatus.OK);

        return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);
    }
}
