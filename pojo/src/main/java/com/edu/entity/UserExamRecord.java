package com.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserExamRecord {
    private long id;
    private Long userId;
    private Long examId;
    private Long courseId;
    private BigDecimal score;
    private Boolean isPassed;
    private Integer status;
    private Integer attemptCount;
    private LocalDateTime startTime;
    private LocalDateTime submitTime;
    private List<QuestionAnswer> answers;  // JSON格式
    private List<String> review;   // JSON格式
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // 关联用户
    private User user;

    // 关联考试
    private Exam exam;

    // 关联课程
    private Course course;

}
