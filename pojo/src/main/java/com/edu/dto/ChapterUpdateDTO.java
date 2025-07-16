package com.edu.dto;

import lombok.Data;

@Data
public class ChapterUpdateDTO {
    private long id;
    private String title;
    private String description;
    private Integer sortOrder;
    private Integer contentType;
    private String content;
    private Integer estimatedTime;
    private Integer isRequired;
    private long courseId;
}
