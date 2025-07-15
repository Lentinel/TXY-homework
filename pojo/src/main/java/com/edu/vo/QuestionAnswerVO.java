package com.edu.vo;

import com.edu.entity.QuestionAnswer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionAnswerVO {
    private Integer questionType;
    private String content;
    private List<String> options;
    private int score;
    private int scoreGet;
    private Integer sortOrder;
    private QuestionAnswer answer;
    private String rightAnswer;
}
