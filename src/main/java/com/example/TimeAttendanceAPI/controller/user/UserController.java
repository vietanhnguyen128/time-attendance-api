package com.example.TimeAttendanceAPI.controller.user;

import com.example.TimeAttendanceAPI.dto.PagedResponse;
import com.example.TimeAttendanceAPI.dto.PasswordDTO;
import com.example.TimeAttendanceAPI.dto.UserDTO;
import com.example.TimeAttendanceAPI.dto.UserInfoDTO;
import com.example.TimeAttendanceAPI.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Chỉnh sửa thông tin tài khoản bất kì, dành cho admin")
    @ApiResponse(
            responseCode = "200",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoDTO.class))}
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserInfoDTO> updateUserInfoAdmin(
            @Parameter(description = "Id của tài khoản muốn sửa") @PathVariable("id") Integer userId,
            @RequestBody UserInfoDTO userDTO) {
        return new ResponseEntity<>(userService.updateUserInfoAdmin(userDTO), HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Chỉnh sửa thông tin tài khoản cá nhân)")
    @ApiResponse(
            responseCode = "200",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoDTO.class))}
    )
    public ResponseEntity<UserInfoDTO> updateUserInfo(@PathVariable("id") Integer userId, @RequestBody UserInfoDTO userDTO) {
        return new ResponseEntity<>(userService.updateUserInfo(userDTO), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Lấy chi tiết tài khoản")
    @ApiResponse(
            responseCode = "200",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoDTO.class))}
    )
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable("id") Integer userId) {
        return new ResponseEntity<>(userService.getUserInfo(userId), HttpStatus.OK);
    }

    @GetMapping("/user")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Lấy danh sách tài khoản")
    @ApiResponse(
            responseCode = "200",
            content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserInfoDTO.class)))}
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedResponse> getUserList(
            @Parameter(description = "Số trang, mặc định: 0") @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
            @Parameter(description = "Kích thước trang, mặc định: 20") @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
            @Parameter(description = "Sắp xếp theo, mặc định: userId tăng dần") @RequestParam(name = "sortBy", defaultValue = "+userId") String sortBy,
            @Parameter(description = "Role cần tìm, mặc định: rỗng") @RequestParam(defaultValue = "") String role) {
        return new ResponseEntity<>(userService.getUserList(pageNo, pageSize, sortBy, role), HttpStatus.OK);
    }

    @PostMapping("/user/change-password")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Đổi mật khẩu")
    public ResponseEntity<String> changePassword(@RequestBody PasswordDTO request) {
        userService.changePassword(request);
        return new ResponseEntity<>("Password successfully changed.", HttpStatus.OK);
    }

    @PostMapping("/user/reset-password")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Reset mật khẩu tài khoản bất kì, dành cho admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordDTO request) {
        userService.resetPassword(request);
        return new ResponseEntity<>("Password successfully reset", HttpStatus.OK);
    }
}
