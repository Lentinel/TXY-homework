package com.edu.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VideoLearningVO {
    // 视频基础信息
    private Long videoId;
    private String videoName;
    private Integer totalSeconds; // 视频总时长（秒）
    private String videoUrl;      // 视频地址（可选）

    // 学习进度信息
    private Integer progressSeconds; // 当前观看进度（秒）
    private Double progressPercent;  // 进度百分比（progressSeconds / totalSeconds）
    private Boolean isCompleted;     // 是否完成
    private LocalDateTime lastLearnTime; // 最后学习时间
    private String totalLearnDurationStr; // 累计学习时长（格式化字符串，如 "01:23:45"）
}