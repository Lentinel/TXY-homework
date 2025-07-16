package com.edu.dto;

import lombok.Data;

@Data
public class QuestionUpdateDTO {
    private Integer questionType;
    private String content;
    private String options;
    private String answer;
    private Integer difficulty;
    private Integer score;
}