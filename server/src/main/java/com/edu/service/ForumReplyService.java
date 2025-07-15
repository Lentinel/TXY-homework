package com.edu.service;

import com.edu.entity.ForumReply;

import java.util.List;

public interface ForumReplyService {
    // 发布回复
    ForumReply createReply(ForumReply reply);

    // 分页查询帖子回复
    List<ForumReply> getRepliesByPostId(Long postId, Integer page, Integer size);

    // 回复点赞/取消点赞
    boolean toggleReplyLike(Long replyId, Long userId);
}

