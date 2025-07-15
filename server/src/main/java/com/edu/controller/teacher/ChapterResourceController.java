package com.edu.controller.teacher;

import com.edu.entity.ChapterResource;
import com.edu.result.Result;
import com.edu.service.LearningResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/chapters/{chapterId}/resources")
public class ChapterResourceController {

    @Autowired
    private LearningResourceService resourceService;

    @PostMapping
    public Result<ChapterResource> createChapterResource(
            @PathVariable Long chapterId,
            @RequestParam("courseId") Long courseId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("resourceType") Integer resourceType,
            @RequestParam(value = "sortOrder", defaultValue = "0") Integer sortOrder,
            @RequestParam("chapterOrder") Integer chapterOrder,
            @RequestParam(value = "prerequisiteId", required = false) Long prerequisiteId,
            @RequestParam("isQuiz") Boolean isQuiz,
            @RequestParam("uploaderId") Long uploaderId) {

        try {
            ChapterResource resource = resourceService.createChapterResource(
                    courseId, chapterId, file, title, description, resourceType,
                    sortOrder, chapterOrder, prerequisiteId, isQuiz, uploaderId);
            return Result.success(resource);
        } catch (IOException e) {
            return Result.error("上传失败: " + e.getMessage());
        }
    }

    @GetMapping
    public Result<List<ChapterResource>> listChapterResources(@PathVariable Long chapterId) {
        List<ChapterResource> resources = resourceService.listChapterResources(chapterId);
        return Result.success(resources);
    }
}