package com.edu.service;

import com.edu.entity.ChapterProgress;

import java.util.List;

public interface ChapterProgressService {
    // 获取用户章节进度
    ChapterProgress getProgress(Long userId, Long chapterId);

    // 开始学习章节
    ChapterProgress startChapter(Long userId, Long chapterId, Long courseId);

    // 更新学习进度
    ChapterProgress updateProgress(Long userId, Long chapterId, Double progress, Integer studyDuration);

    // 完成章节学习
    ChapterProgress completeChapter(Long userId, Long chapterId, Double score, Boolean isPassed);

    // 获取用户课程所有章节进度
    List<ChapterProgress> getCourseProgress(Long userId, Long courseId);

    // 计算课程完成度
    double calculateCourseCompletionRate(Long userId, Long courseId);
}

