package com.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamQuestion {
    private Long examId;
    private Long questionId;
    private Integer sortOrder;
    private Integer score; // 本题在本次考试中的分值
    private Question question; // 关联的试题详情
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}