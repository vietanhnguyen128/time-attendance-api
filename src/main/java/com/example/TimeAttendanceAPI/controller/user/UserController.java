package com.example.TimeAttendanceAPI.controller.user;

import com.example.TimeAttendanceAPI.dto.UserDTO;
import com.example.TimeAttendanceAPI.dto.UserInfoDTO;
import com.example.TimeAttendanceAPI.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/admin/{id}")
    public ResponseEntity<?> updateUserInfoAdmin(@PathVariable("id") Integer userId, @RequestBody UserInfoDTO userDTO) {
        userService.updateUserInfoAdmin(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserInfo(@PathVariable("id") Integer userId, @RequestBody UserInfoDTO userDTO) {
        userService.updateUserInfo(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable("id") Integer userId) {
        return new ResponseEntity<>(userService.getUserInfo(userId), HttpStatus.OK);
    }
}
