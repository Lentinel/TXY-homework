package com.edu.controller;

import com.edu.result.Result;
import com.edu.entity.ForumSection;
import com.edu.entity.ForumPost;
import com.edu.service.ForumSectionService;
import com.edu.service.ForumPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/forum")
public class ForumHomeController {
    @Autowired
    private ForumSectionService sectionService;
    @Autowired
    private ForumPostService postService;

    // 获取所有板块
    @GetMapping("/sections")
    public Result<List<ForumSection>> getAllSections() {
        return Result.success(sectionService.getAllEnabledSections());
    }

    // 获取热门帖子
    @GetMapping("/hot-posts")
    public Result<List<ForumPost>> getHotPosts() {
        return Result.success(postService.getHotPosts(10));
    }
}