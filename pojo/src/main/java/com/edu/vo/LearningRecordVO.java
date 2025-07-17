package com.edu.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LearningRecordVO {
    // 资源信息（冗余，避免联表查询）
    private Long courseId;
    private String courseName;
    private Long chapterId;
    private String chapterName;
    private Long videoId;
    private String videoName;
    private Integer totalSeconds; // 视频总时长（秒）

    // 学习数据
    private Integer progressSeconds; // 当前进度（秒）
    private Double progressPercent; // 进度百分比（progressSeconds / totalSeconds）
    private Boolean isCompleted; // 是否完成
    private LocalDateTime lastLearnTime; // 最后学习时间
    private String totalLearnDurationStr; // 累计时长（格式化：如"01:23:45"）
}