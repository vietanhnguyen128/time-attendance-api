package com.example.TimeAttendanceAPI.controller.holiday;

import com.example.TimeAttendanceAPI.dto.HolidayDTO;
import com.example.TimeAttendanceAPI.dto.PagedResponse;
import com.example.TimeAttendanceAPI.dto.UserInfoDTO;
import com.example.TimeAttendanceAPI.model.Holiday;
import com.example.TimeAttendanceAPI.service.holiday.HolidayService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HolidayController {
    private final HolidayService holidayService;

    @PostMapping("/holiday")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Tạo ngày nghỉ lễ")
    @ApiResponse(
            responseCode = "200",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = HolidayDTO.class))}
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HolidayDTO> createHoliday(@RequestBody HolidayDTO request) {
        return new ResponseEntity<>(holidayService.createHoliday(request), HttpStatus.OK);
    }

    @GetMapping("/holiday/{id}")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Lấy chi tiết ngày nghỉ lễ")
    @ApiResponse(
            responseCode = "200",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = HolidayDTO.class))}
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HolidayDTO> getHoliday(@PathVariable("id") long holidayId) {
        return new ResponseEntity<>(holidayService.getHoliday(holidayId), HttpStatus.OK);
    }

    @GetMapping("/holiday")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Lấy danh sách ngày nghỉ lễ")
    @ApiResponse(
            responseCode = "200",
            content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = HolidayDTO.class)))}
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedResponse> getHolidayList(
            @Parameter(description = "Số trang, mặc định: 0") @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
            @Parameter(description = "Kích thước trang, mặc định: 20") @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
            @Parameter(description = "Sắp xếp theo, mặc định: holidayId tăng dần") @RequestParam(name = "sortBy", defaultValue = "+holidayId") String sortBy) {
        return new ResponseEntity<>(holidayService.getHolidayList(pageNo, pageSize, sortBy), HttpStatus.OK);
    }

    @PutMapping("/holiday/{id}")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Chỉnh sửa thông tin ngày nghỉ lễ")
    @ApiResponse(
            responseCode = "200",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = HolidayDTO.class))}
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HolidayDTO> updateHoliday(@PathVariable("id") long id, @RequestBody HolidayDTO request) {
        return new ResponseEntity<>(holidayService.updateHoliday(request), HttpStatus.OK);
    }

    @DeleteMapping("/holiday/{id}")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Xóa ngày nghỉ lễ")
    @ApiResponse(
            responseCode = "204",
            description = "Delete success"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteHoliday(@PathVariable("id") long holidayId) {
        holidayService.deleteHoliday(holidayId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
