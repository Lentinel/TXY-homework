package com.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserExamRecord {
    private Long id;
    private Long userId;
    private Long examId;
    private Long courseId;
    private BigDecimal score;
    private Integer isPassed; // 0-否，1-是
    private Integer status; // 1-进行中，2-已提交，3-已批改
    private Integer attemptCount;
    private LocalDateTime startTime;
    private LocalDateTime submitTime;
    private String answers; // JSON：{questionId: 答案}
    private String review; // 批改评语
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
