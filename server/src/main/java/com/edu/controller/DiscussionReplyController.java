package com.edu.controller;

import com.edu.entity.DiscussionReply;
import com.edu.result.Result;
import com.edu.service.DiscussionReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/replies")
public class DiscussionReplyController {

    @Autowired
    private DiscussionReplyService replyService;

    @PostMapping
    public Result<DiscussionReply> createReply(@RequestBody DiscussionReply reply) {
        DiscussionReply result = replyService.createReply(reply);
        return Result.success(result);
    }

    @GetMapping("/discussion/{discussionId}")
    public Result<List<DiscussionReply>> listRepliesByDiscussion(
            @PathVariable Long discussionId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        List<DiscussionReply> replies = replyService.listRepliesByDiscussion(discussionId, page, size);
        return Result.success(replies);
    }

    @PostMapping("/{replyId}/best")
    public Result<Boolean> markBestReply(
            @PathVariable Long replyId,
            @RequestParam Long discussionId,
            @RequestParam Long userId) {
        boolean success = replyService.markBestReply(discussionId, replyId, userId);
        return Result.success(success);
    }

    @PostMapping("/{replyId}/like")
    public Result<Boolean> toggleLikeReply(
            @PathVariable Long replyId,
            @RequestParam Long userId) {
        boolean isLiked = replyService.toggleLikeReply(replyId, userId);
        return Result.success(isLiked);
    }
}