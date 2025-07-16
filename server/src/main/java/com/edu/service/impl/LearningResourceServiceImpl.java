package com.edu.service.impl;

import com.edu.entity.ChapterResource;
import com.edu.entity.CourseResource;
import com.edu.mapper.ChapterResourceMapper;
import com.edu.mapper.CourseResourceMapper;
import com.edu.service.FileStorageService;
import com.edu.service.LearningResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LearningResourceServiceImpl implements LearningResourceService {

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private AliOssUtil aliOssUtil;  // 注入Spring管理的AliOssUtil实例

    @Autowired
    private CourseResourceMapper courseResourceMapper;

    @Autowired
    private ChapterResourceMapper chapterResourceMapper;

    @Override
    public CourseResource createCourseResource(
            Long courseId, MultipartFile file, String title, String description,
            Integer resourceType, Integer sortOrder, String courseOverview,
            Boolean isRequired, Long uploaderId) throws IOException {

        // 构建存储路径
        String filePath = buildCourseResourcePath(courseId, file.getOriginalFilename());

        // 上传文件
        String url = fileStorageService.upload(file, filePath);

        // 创建基础资源
        CourseResource resource = CourseResource.builder()
                .courseId(courseId)
                .title(title)
                .description(description)
                .resourceType(resourceType)
                .url(url)
                .size(file.getSize())
                .sortOrder(sortOrder)
                .uploaderId(uploaderId)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .courseOverview(courseOverview)
                .isRequired(isRequired)
                .build();

        // 保存资源
        courseResourceMapper.insert(resource);
        return resource;
    }

    @Override
    public ChapterResource createChapterResource(
            Long courseId, Long chapterId, MultipartFile file, String title,
            String description, Integer resourceType, Integer sortOrder,
            Integer chapterOrder, Long prerequisiteId, Boolean isQuiz,
            Long uploaderId) throws IOException {

        // 构建存储路径
        String filePath = buildChapterResourcePath(courseId, chapterId, file.getOriginalFilename());

        // 上传文件
        String url = fileStorageService.upload(file, filePath);

        // 创建基础资源
        ChapterResource resource = ChapterResource.builder()
                .courseId(courseId)
                .chapterId(chapterId)
                .title(title)
                .description(description)
                .resourceType(resourceType)
                .url(url)
                .size(file.getSize())
                .sortOrder(sortOrder)
                .uploaderId(uploaderId)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .chapterOrder(chapterOrder)
                .prerequisiteId(prerequisiteId)
                .isQuiz(isQuiz)
                .build();

        // 保存资源
        chapterResourceMapper.insert(resource);
        return resource;
    }

    @Override
    public List<CourseResource> listCourseResources(Long courseId) {
        return courseResourceMapper.selectByCourseId(courseId);
    }

    @Override
    public List<ChapterResource> listChapterResources(Long chapterId) {
        return chapterResourceMapper.selectByChapterId(chapterId);
    }

    @Override
    public void downloadResource(Long resourceId, OutputStream outputStream) {
        // 先尝试从课程资源查询
        CourseResource courseResource = courseResourceMapper.selectById(resourceId);
        if (courseResource != null) {
            try {
                fileStorageService.download(courseResource.getUrl(), outputStream);
                return;
            } catch (IOException e) {
                throw new RuntimeException("下载资源失败", e);
            }
        }

        // 再尝试从章节资源查询
        ChapterResource chapterResource = chapterResourceMapper.selectById(resourceId);
        if (chapterResource != null) {
            try {
                fileStorageService.download(chapterResource.getUrl(), outputStream);
                return;
            } catch (IOException e) {
                throw new RuntimeException("下载资源失败", e);
            }
        }

        throw new RuntimeException("资源不存在");
    }

    private String buildCourseResourcePath(Long courseId, String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String extension = getFileExtension(originalFilename);
        return "courses/" + courseId + "/resources/" + uuid + "." + extension;
    }

    private String buildChapterResourcePath(Long courseId, Long chapterId, String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String extension = getFileExtension(originalFilename);
        return "courses/" + courseId + "/chapters/" + chapterId + "/resources/" + uuid + "." + extension;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}