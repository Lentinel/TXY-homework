package com.edu.service;

import com.edu.entity.ChapterResource;
import com.edu.entity.CourseResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface LearningResourceService {
    // 课程资源管理
    CourseResource createCourseResource(
            Long courseId, MultipartFile file, String title, String description,
            Integer resourceType, Integer sortOrder, String courseOverview,
            Boolean isRequired, Long uploaderId) throws IOException;

    List<CourseResource> listCourseResources(Long courseId);

    // 章节资源管理
    ChapterResource createChapterResource(
            Long courseId, Long chapterId, MultipartFile file, String title,
            String description, Integer resourceType, Integer sortOrder,
            Integer chapterOrder, Long prerequisiteId, Boolean isQuiz,
            Long uploaderId) throws IOException;

    List<ChapterResource> listChapterResources(Long chapterId);

    // 通用资源管理
    void downloadResource(Long resourceId, OutputStream outputStream);
}