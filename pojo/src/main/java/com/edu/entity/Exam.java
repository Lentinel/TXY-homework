package com.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exam {
    private long id;
    private Long courseId;
    private String title;
    private String description;
    private Integer duration;
    private Integer passingScore;
    private Boolean isFinalExam;
    private Integer coverageType;
    private Integer status;
    private Integer maxAttempts;
    private Boolean shuffleQuestions;
    private Boolean shuffleOptions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // 关联课程
    private Course course;

    // 关联试题
    private List<Question> questions;

    // 关联考试记录
    private List<UserExamRecord> examRecords;
}