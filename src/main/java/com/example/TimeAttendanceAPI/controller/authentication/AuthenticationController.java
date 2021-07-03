package com.example.TimeAttendanceAPI.controller.authentication;

import com.example.TimeAttendanceAPI.dto.ErrorResponse;
import com.example.TimeAttendanceAPI.dto.UserDTO;
import com.example.TimeAttendanceAPI.security.JwtResponse;
import com.example.TimeAttendanceAPI.security.RegisterRequest;
import com.example.TimeAttendanceAPI.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "Đăng nhập")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad credentials",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid UserDTO loginRequest) {
        return new ResponseEntity<>(authenticationService.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/register")
    @Operation(summary = "Tạo tài khoản mới", description = "Admin tạo tài khoản mới. Trường 'role' chỉ nhận 3 giá trị: ROLE_ADMIN, ROLE_MANAGER, ROLE_EMPLOYEE")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(mediaType = "plain/text", schema = @Schema(type = "string"))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Username already exists",
                    content = @Content(mediaType = "plain/text", schema = @Schema(type = "string"))
            )
    })
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegisterRequest registerRequest) {
        boolean result = authenticationService.createUser(registerRequest);
        if (result) {
            return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Username is already taken", HttpStatus.CONFLICT);
        }
    }
}
