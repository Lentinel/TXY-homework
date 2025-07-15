package com.edu.dto;

import lombok.Data;

@Data
public class CreateCourseDTO {
    private String name;
    private String description;
    private Integer sortOrder;
    private long parentId;
}
