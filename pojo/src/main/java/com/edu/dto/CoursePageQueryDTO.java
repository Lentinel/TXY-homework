package com.edu.dto;

import lombok.Data;

@Data
public class CoursePageQueryDTO {
    private int page;
    private int pageSize;

    private String title;

    private Integer recommend;

    private long categoryId;
}
