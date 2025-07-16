package com.edu.dto;

import lombok.Data;

@Data
public class UpdateQuestionDTO {
    private long id;
    private String content;
    private String options;  // JSON格式
    private String answer;   // JSON格式
    private Integer difficulty;
    private Integer score;


    // 关联考试
}
