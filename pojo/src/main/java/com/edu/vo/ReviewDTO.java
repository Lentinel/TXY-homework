package com.edu.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ReviewDTO {
    private BigDecimal totalScore;
    private String reviewContent;
    private Map<Long, Integer> subjectiveScores; // 主观题得分
}