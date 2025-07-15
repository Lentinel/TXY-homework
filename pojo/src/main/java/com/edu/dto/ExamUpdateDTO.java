package com.edu.dto;

import lombok.Data;

@Data
public class ExamUpdateDTO {
    private long id;
    private long courseId;
    private String title;
    private String description;
    private Integer duration;
    private Integer passingScore;
    private Integer status;
    private Integer maxAttempts;

}
