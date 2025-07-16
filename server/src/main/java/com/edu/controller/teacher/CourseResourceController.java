package com.edu.controller.teacher;

import com.edu.entity.CourseResource;
import com.edu.result.Result;
import com.edu.service.LearningResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/courses/{courseId}/resources")
public class CourseResourceController {

    @Autowired
    private LearningResourceService resourceService;

    @PostMapping
    public Result<CourseResource> createCourseResource(
            @PathVariable Long courseId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("resourceType") Integer resourceType,
            @RequestParam(value = "sortOrder", defaultValue = "0") Integer sortOrder,
            @RequestParam("courseOverview") String courseOverview,
            @RequestParam("isRequired") Boolean isRequired,
            @RequestParam("uploaderId") Long uploaderId) {

        try {
            CourseResource resource = resourceService.createCourseResource(
                    courseId, file, title, description, resourceType, sortOrder,
                    courseOverview, isRequired, uploaderId);
            return Result.success(resource);
        } catch (IOException e) {
            return Result.error("上传失败: " + e.getMessage());
        }
    }

    @GetMapping
    public Result<List<CourseResource>> listCourseResources(@PathVariable Long courseId) {
        List<CourseResource> resources = resourceService.listCourseResources(courseId);
        return Result.success(resources);
    }
}