package com.edu.dto;

import lombok.Data;

@Data
public class CreateQuestionDTO {
    private int sortOrder;
    private Integer questionType;
    private String content;
    private String options;
    private String answer;
    private String difficulty;
    private Integer score;
    private long creatorId;
    private long examId;
}
