package com.edu.dto;

import lombok.Data;

@Data
public class ExamQuestionDTO {
    private Long questionId;
    private Integer score; // 可选，若为空则使用试题默认分值
}