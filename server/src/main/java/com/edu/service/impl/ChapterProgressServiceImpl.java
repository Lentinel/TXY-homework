package com.edu.service.impl;

import com.edu.entity.ChapterProgress;
import com.edu.mapper.ChapterMapper;
import com.edu.mapper.ChapterProgressMapper;
import com.edu.service.ChapterProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ChapterProgressServiceImpl implements ChapterProgressService {
    @Autowired
    private ChapterProgressMapper progressMapper;
    @Autowired
    private ChapterMapper chapterMapper;

    @Override
    public ChapterProgress getProgress(Long userId, Long chapterId) {
        return progressMapper.selectByUserAndChapter(userId, chapterId);
    }

    @Transactional
    @Override
    public ChapterProgress startChapter(Long userId, Long chapterId, Long courseId) {
        // 检查是否已存在进度记录
        ChapterProgress progress = progressMapper.selectByUserAndChapter(userId, chapterId);
        if (progress != null) {
            return progress;
        }

        // 创建新记录
        progress = ChapterProgress.builder()
                .userId(userId)
                .chapterId(chapterId)
                .courseId(courseId)
                .status(1) // 进行中
                .startTime(LocalDateTime.now())
                .progress(0.0)
                .studyDuration(0)
                .build();

        progressMapper.insert(progress);
        return progress;
    }

    @Transactional
    @Override
    public ChapterProgress updateProgress(Long userId, Long chapterId, Double progressPercent, Integer studyDuration) {
        ChapterProgress progress = progressMapper.selectByUserAndChapter(userId, chapterId);
        if (progress == null) {
            throw new IllegalArgumentException("章节进度记录不存在");
        }

        // 更新进度和学习时长
        progress.setProgress(progressPercent);
        progress.setStudyDuration(progress.getStudyDuration() + studyDuration);
        progress.setStatus(progressPercent >= 100.0 ? 2 : 1); // 如果进度达到100%，标记为已完成

        if (progress.getStatus() == 2 && progress.getCompleteTime() == null) {
            progress.setCompleteTime(LocalDateTime.now());
        }

        progressMapper.update(progress);
        return progress;
    }

    @Transactional
    @Override
    public ChapterProgress completeChapter(Long userId, Long chapterId, Double score, Boolean isPassed) {
        ChapterProgress progress = progressMapper.selectByUserAndChapter(userId, chapterId);
        if (progress == null) {
            throw new IllegalArgumentException("章节进度记录不存在");
        }

        // 更新完成状态
        progress.setStatus(2);
        progress.setProgress(100.0);
        progress.setCompleteTime(LocalDateTime.now());
        progress.setScore(score);
        progress.setIsPassed(isPassed);

        progressMapper.update(progress);
        return progress;
    }

    @Override
    public List<ChapterProgress> getCourseProgress(Long userId, Long courseId) {
        return progressMapper.selectByUserAndCourse(userId, courseId);
    }

    @Override
    public double calculateCourseCompletionRate(Long userId, Long courseId) {
        int completedCount = progressMapper.countCompletedByUserAndCourse(userId, courseId);
        int totalCount = progressMapper.countTotalChapters(courseId);

        if (totalCount == 0) {
            return 0.0;
        }

        return (double) completedCount / totalCount * 100.0;
    }
}