package com.edu.vo;

import lombok.Data;

import java.util.List;

// 返回VO（课程级详情）
@Data
public class CourseLearningVO {
    private Long courseId;
    private String courseName;
    private Double completionRate; // 课程完成率（章节完成数/总章节数）
    private String totalLearnDurationStr; // 总学习时长（格式化）
    private List<ChapterLearningVO> chapters; // 章节列表及进度
}
