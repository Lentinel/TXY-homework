package com.edu.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterProgress {
    private Long id;
    private Long userId;
    private Long chapterId;
    private Long courseId;
    private Integer status; // 0-未开始，1-进行中，2-已完成
    private LocalDateTime startTime;
    private LocalDateTime completeTime;
    private Integer studyDuration; // 秒
    private Double progress; // 进度百分比
    private Boolean isPassed;
    private Double score;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 关联对象
    private Chapter chapter;
    private User user;
}