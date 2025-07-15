package com.edu.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TeacherCourseDTO {
    private long instructorId;
    private String title;
    private String description;
    private Integer Duration;
    private BigDecimal price;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer difficulty;
    private long categoryId;
}
