package com.example.TimeAttendanceAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagedResponse {
    private int totalPages;

    private long totalItems;

    private int currentPage;

    private List<?> data;
}
