package com.example.TimeAttendanceAPI.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtils {
    public static Pageable createPageable(int pageNo, int pageSize, String sortBy) {
        if (StringUtils.isNotBlank(sortBy)) {
            if (sortBy.startsWith("+")) {
                return PageRequest.of(pageNo, pageSize, Sort.by(sortBy.substring(1)));
            } else if (sortBy.startsWith("-")) {
                return PageRequest.of(pageNo, pageSize, Sort.by(sortBy.substring(1)).descending());
            } else {
                throw new RuntimeException("Invalid parameter.");
            }
        }

        return PageRequest.of(pageNo, pageSize, Sort.unsorted());
    }
}
