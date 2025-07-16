package com.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Question {
    private long id;
    private Integer questionType;
    private String content;
    private String options;  // JSON格式
    private String answer;   // JSON格式
    private Integer difficulty;
    private Integer score;
    private Long creatorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // 关联考试
    private List<Exam> exams;
}
