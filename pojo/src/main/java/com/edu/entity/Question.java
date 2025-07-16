package com.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Question {private Long id;
    private Integer questionType; // 1-单选，2-多选，3-判断，4-填空，5-简答
    private String content;
    private String options; // JSON格式
    private String answer; // 参考答案（JSON）
    private Integer difficulty;
    private Integer score; // 默认分值
    private Long creatorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
