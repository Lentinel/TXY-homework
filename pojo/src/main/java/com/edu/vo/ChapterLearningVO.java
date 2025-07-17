package com.edu.vo;

import lombok.Data;

import java.util.List;

// 章节级VO
@Data
public class ChapterLearningVO {
    private Long chapterId;
    private String chapterName;
    private Boolean isCompleted;
    private List<VideoLearningVO> videos; // 视频列表及进度
}