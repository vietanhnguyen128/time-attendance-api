package com.example.TimeAttendanceAPI.controller.user;

import com.example.TimeAttendanceAPI.dto.UserDTO;
import com.example.TimeAttendanceAPI.dto.UserInfoDTO;
import com.example.TimeAttendanceAPI.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/user/admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUserInfoAdmin(@PathVariable("id") Integer userId, @RequestBody UserInfoDTO userDTO) {
        userService.updateUserInfoAdmin(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> updateUserInfo(@PathVariable("id") Integer userId, @RequestBody UserInfoDTO userDTO) {
        userService.updateUserInfo(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> getUserInfo(@PathVariable("id") Integer userId) {
        return new ResponseEntity<>(userService.getUserInfo(userId), HttpStatus.OK);
    }

    @GetMapping("/user/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserList(@RequestParam String role) {
        return new ResponseEntity<>(userService.getUserList(role), HttpStatus.OK);
    }
}
