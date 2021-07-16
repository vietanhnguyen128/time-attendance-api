package com.example.TimeAttendanceAPI.controller.form_record;

import com.example.TimeAttendanceAPI.dto.FormRecordDTO;
import com.example.TimeAttendanceAPI.dto.PagedResponse;
import com.example.TimeAttendanceAPI.service.form_record.FormRecordService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.Normalizer;

@RestController
@RequiredArgsConstructor
public class FormRecordController {
    private final FormRecordService formRecordService;

    @PostMapping("/form")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Tạo đơn nghỉ phép")
    public ResponseEntity<FormRecordDTO> createForm(@RequestBody @Valid FormRecordDTO request) {
        return new ResponseEntity<>(formRecordService.createFormRecord(request), HttpStatus.OK);
    }

    @GetMapping("/form")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Lấy danh sách đơn nghỉ phép")
    @ApiResponse(
            responseCode = "200",
            content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FormRecordDTO.class)))}
    )
    public ResponseEntity<PagedResponse> getFormRecordList(
            @Parameter(description = "Số trang, mặc định: 0") @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @Parameter(description = "Kích thước trang, mặc định: 20") @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @Parameter(description = "Sắp xếp theo, mặc định: id tăng dần") @RequestParam(value = "sortBy", defaultValue = "+id") String sortBy) {
        return new ResponseEntity<>(formRecordService.getFormRecordList(pageNo, pageSize, sortBy), HttpStatus.OK);
    }

    @GetMapping("/form/managed")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Lấy danh sách đơn nghỉ phép của cấp dưới")
    @ApiResponse(
            responseCode = "200",
            content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FormRecordDTO.class)))}
    )
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<PagedResponse> getSubordinatesRecordList(
            @Parameter(description = "Số trang, mặc định: 0") @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @Parameter(description = "Kích thước trang, mặc định: 20") @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @Parameter(description = "Sắp xếp theo, mặc định: id tăng dần") @RequestParam(value = "sortBy", defaultValue = "+id") String sortBy) {
        return new ResponseEntity<>(formRecordService.getSubordinatesFormList(pageNo, pageSize, sortBy), HttpStatus.OK);
    }

    @GetMapping("/form/{id}")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Lấy chi tiết của đơn nghỉ phép")
    public ResponseEntity<FormRecordDTO> getSingleFormRecord(@PathVariable("id") int formId) {
        return new ResponseEntity<>(formRecordService.getSingleFormRecord(formId), HttpStatus.OK);
    }

    @PutMapping("/form/{id}")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Chỉnh sửa đơn nghỉ phép")
    public ResponseEntity<FormRecordDTO> updateFormRecord(@PathVariable("id") int formId, @RequestBody @Valid FormRecordDTO request) {
        return new ResponseEntity<>(formRecordService.updateFormRecord(request), HttpStatus.OK);
    }

    @PutMapping("/form/process/{id}")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Duyệt đơn nghỉ phép",
            description = "Duyệt đơn nghỉ phép. Chỉ dành cho ROLE_ADMIN và ROLE_MANAGER. Nhận 3 giá trị status: PENDING, ACCEPTED, DECLINED")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<FormRecordDTO> processFormRecord(@PathVariable("id") int formId, @RequestBody @Valid FormRecordDTO request) {
        return new ResponseEntity<>(formRecordService.processFormRecord(request), HttpStatus.OK);
    }

    @DeleteMapping("/form/{id}")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            summary = "Xóa đơn nghỉ phép",
            description = "Xóa đơn nghỉ phép. Không thể xóa nếu đơn đã được duyệt")
    @ApiResponse(
            responseCode = "204",
            description = "Delete success"
    )
    public ResponseEntity<?> deleteFormRecord(@PathVariable("id") int formId) {
        formRecordService.deleteFormRecord(formId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
