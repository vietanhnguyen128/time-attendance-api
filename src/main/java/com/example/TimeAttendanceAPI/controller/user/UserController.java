package com.example.TimeAttendanceAPI.controller.user;

import com.example.TimeAttendanceAPI.dto.PagedResponse;
import com.example.TimeAttendanceAPI.dto.PasswordDTO;
import com.example.TimeAttendanceAPI.dto.UserDTO;
import com.example.TimeAttendanceAPI.dto.UserInfoDTO;
import com.example.TimeAttendanceAPI.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(
            responseCode = "200",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoDTO.class))}
    )
    public ResponseEntity<UserInfoDTO> updateUserInfoAdmin(@PathVariable("id") Integer userId, @RequestBody UserInfoDTO userDTO) {
        return new ResponseEntity<>(userService.updateUserInfoAdmin(userDTO), HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_EMPLOYEE')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(
            responseCode = "200",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoDTO.class))}
    )
    public ResponseEntity<UserInfoDTO> updateUserInfo(@PathVariable("id") Integer userId, @RequestBody UserInfoDTO userDTO) {
        return new ResponseEntity<>(userService.updateUserInfo(userDTO), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_EMPLOYEE')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(
            responseCode = "200",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoDTO.class))}
    )
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable("id") Integer userId) {
        return new ResponseEntity<>(userService.getUserInfo(userId), HttpStatus.OK);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(
            responseCode = "200",
            content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserInfoDTO.class)))}
    )
    public ResponseEntity<PagedResponse> getUserList(@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
                                                     @RequestParam(name = "sortBy", defaultValue = "") String sortBy,
                                                     @RequestParam(defaultValue = "") String role) {
        return new ResponseEntity<>(userService.getUserList(pageNo, pageSize, sortBy, role), HttpStatus.OK);
    }

    @PostMapping("/user/change-password")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_EMPLOYEE')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<String> changePassword(@RequestBody PasswordDTO request) {
        userService.changePassword(request);
        return new ResponseEntity<>("Password successfully changed.", HttpStatus.OK);
    }
}
