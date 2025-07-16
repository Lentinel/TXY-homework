package com.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionAnswer {
    private long id;
    private String title;
    private String content;
    private long userId;
    private long questionId;
    private long examId;
    private LocalDateTime CreatedAt;
    private LocalDateTime updatedAt;
    private long sortOrder;
    private long score;


}
