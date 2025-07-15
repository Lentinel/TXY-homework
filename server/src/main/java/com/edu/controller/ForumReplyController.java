package com.edu.controller;

import com.edu.result.Result;
import com.edu.entity.ForumReply;
import com.edu.service.ForumReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/replies")
public class ForumReplyController {
    @Autowired
    private ForumReplyService replyService;

    // 发布回复
    @PostMapping
    public Result<ForumReply> createReply(@PathVariable Long postId, @RequestBody ForumReply reply) {
        reply.setPostId(postId);
        return Result.success(replyService.createReply(reply));
    }

    // 获取帖子回复列表
    @GetMapping
    public Result<List<ForumReply>> getReplies(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        return Result.success(replyService.getRepliesByPostId(postId, page, size));
    }

    // 回复点赞
    @PostMapping("/{replyId}/like")
    public Result<Boolean> likeReply(@PathVariable Long replyId, @RequestParam Long userId) {
        return Result.success(replyService.toggleReplyLike(replyId, userId));
    }
}