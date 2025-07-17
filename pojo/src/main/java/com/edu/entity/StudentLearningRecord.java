package com.edu.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentLearningRecord {
    private Long id;
    private Long userId;
    private Long courseId;
    private Long chapterId;
    private Long videoId;
    private Integer progressSeconds; // 观看进度（秒）
    private Integer totalSeconds;    // 视频总时长（秒）
    private Integer isCompleted;     // 0-未完成，1-已完成
    private LocalDateTime lastLearnTime;
    private Integer totalLearnDuration; // 累计学习时长（秒）
}