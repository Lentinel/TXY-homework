package com.edu.vo;

import lombok.Data;

import java.util.Map;

// 提交答案DTO
@Data
public class SubmitAnswersDTO {
    private Map<Long, Object> answers; // key: questionId, value: 答案
}
