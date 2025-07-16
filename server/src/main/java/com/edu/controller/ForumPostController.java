package com.edu.controller;

import com.edu.entity.ForumPost;
import com.edu.result.Result;
import com.edu.service.ForumPostService;
import com.edu.service.ForumReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections/{sectionId}/posts")
public class ForumPostController {
    @Autowired
    private ForumPostService postService;
    @Autowired
    private ForumReplyService replyService;

    // 发布帖子
    @PostMapping
    public Result<ForumPost> createPost(@PathVariable Long sectionId, @RequestBody ForumPost post) {
        post.setSectionId(sectionId);
        return Result.success(postService.createPost(post));
    }

    // 分页查询板块帖子
    @GetMapping
    public Result<List<ForumPost>> getSectionPosts(
            @PathVariable Long sectionId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        return Result.success(postService.getPostsBySectionId(sectionId, page, size));
    }

    // 获取帖子详情
    @GetMapping("/{postId}")
    public Result<ForumPost> getPostDetail(@PathVariable Long postId) {
        return Result.success(postService.getPostDetail(postId));
    }

    // 帖子点赞
    @PostMapping("/{postId}/like")
    public Result<Boolean> likePost(@PathVariable Long postId, @RequestParam Long userId) {
        return Result.success(postService.togglePostLike(postId, userId));
    }
}