package com.edu.dto;

import lombok.Data;

@Data
public class TeachCoursePageQueryDTO {
    private int page;
    private int pageSize;
    private long instructorId;
}
