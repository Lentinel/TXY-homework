package com.edu.dto;

import lombok.Data;

@Data
public class CourseCategoryUpdateDTO {
    private long id;
    private long parentId;
    private String name;
    private String description;
    private Integer sortOrder;
}
