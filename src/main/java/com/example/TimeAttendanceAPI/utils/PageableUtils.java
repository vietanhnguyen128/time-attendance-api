package com.example.TimeAttendanceAPI.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtils {
    public static Pageable createPageable(int pageNo, int pageSize, String sortBy) {
        return PageRequest.of(pageNo, pageSize, StringUtils.isNotBlank(sortBy) ? Sort.by(sortBy) : Sort.unsorted());
    }
}
