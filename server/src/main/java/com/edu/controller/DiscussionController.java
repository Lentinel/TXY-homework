package com.edu.controller;

import com.edu.entity.CourseDiscussion;
import com.edu.result.Result;
import com.edu.service.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discussions")

public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;

    @PostMapping

    public Result<CourseDiscussion> createDiscussion(@RequestBody CourseDiscussion discussion) {
        CourseDiscussion result = discussionService.createDiscussion(discussion);
        return Result.success(result);
    }

    @GetMapping("/{id}")

    public Result<CourseDiscussion> getDiscussion(@PathVariable Long id) {
        CourseDiscussion discussion = discussionService.getDiscussionDetail(id);
        return Result.success(discussion);
    }

    @GetMapping("/course/{courseId}")

    public Result<List<CourseDiscussion>> listCourseDiscussions(
            @PathVariable Long courseId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        List<CourseDiscussion> discussions = discussionService.listCourseDiscussions(courseId, page, size);
        return Result.success(discussions);
    }

    @GetMapping("/global")

    public Result<List<CourseDiscussion>> listGlobalDiscussions(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        List<CourseDiscussion> discussions = discussionService.listGlobalDiscussions(page, size);
        return Result.success(discussions);
    }
}