package com.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exam {


    private Long id;
    private Long courseId;
    private String title;
    private String description;
    private Integer duration; // 分钟
    private Integer passingScore;
    private Integer isFinalExam; // 0-否，1-是
    private Integer coverageType; // 1-单章节，2-多章节，3-整课程
    private Integer status; // 1-草稿，2-已发布，3-已归档
    private Integer maxAttempts;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}