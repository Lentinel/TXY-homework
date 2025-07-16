package com.edu.controller.student;

import com.edu.result.Result;
import com.edu.entity.ChapterProgress;
import com.edu.service.ChapterProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ChapterProgressController {
    @Autowired
    private ChapterProgressService progressService;

    // 获取章节进度
    @GetMapping("/{chapterId}")
    public Result<ChapterProgress> getProgress(
            @RequestParam Long userId,
            @PathVariable Long chapterId) {
        return Result.success(progressService.getProgress(userId, chapterId));
    }

    // 开始学习章节
    @PostMapping("/{chapterId}/start")
    public Result<ChapterProgress> startChapter(
            @RequestParam Long userId,
            @RequestParam Long courseId,
            @PathVariable Long chapterId) {
        return Result.success(progressService.startChapter(userId, chapterId, courseId));
    }

    // 更新学习进度
    @PostMapping("/{chapterId}/update")
    public Result<ChapterProgress> updateProgress(
            @RequestParam Long userId,
            @PathVariable Long chapterId,
            @RequestParam Double progress,
            @RequestParam Integer studyDuration) {
        return Result.success(progressService.updateProgress(userId, chapterId, progress, studyDuration));
    }

    // 完成章节学习
    @PostMapping("/{chapterId}/complete")
    public Result<ChapterProgress> completeChapter(
            @RequestParam Long userId,
            @PathVariable Long chapterId,
            @RequestParam(required = false) Double score,
            @RequestParam(required = false) Boolean isPassed) {
        return Result.success(progressService.completeChapter(userId, chapterId, score, isPassed));
    }

    // 获取课程所有章节进度
    @GetMapping("/courses/{courseId}")
    public Result<List<ChapterProgress>> getCourseProgress(
            @RequestParam Long userId,
            @PathVariable Long courseId) {
        return Result.success(progressService.getCourseProgress(userId, courseId));
    }

    // 获取课程完成率
    @GetMapping("/courses/{courseId}/completion")
    public Result<Double> getCourseCompletionRate(
            @RequestParam Long userId,
            @PathVariable Long courseId) {
        return Result.success(progressService.calculateCourseCompletionRate(userId, courseId));
    }
}