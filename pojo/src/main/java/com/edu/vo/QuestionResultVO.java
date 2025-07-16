package com.edu.vo;

import lombok.Data;

@Data
public class QuestionResultVO {
    private Long questionId;
    private String content; // 题目内容
    private Integer questionType; // 题型
    private Object options; // 选项
    private Object userAnswer; // 用户答案
    private Object correctAnswer; // 正确答案
    private Boolean isCorrect; // 是否答对
    private Integer score; // 本题得分
    private Integer maxScore; // 本题满分
}