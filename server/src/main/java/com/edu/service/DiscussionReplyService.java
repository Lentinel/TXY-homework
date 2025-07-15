package com.edu.service;

import com.edu.entity.DiscussionReply;

import java.util.List;

public interface DiscussionReplyService {
    // 创建回复
    DiscussionReply createReply(DiscussionReply reply);

    // 更新回复
    DiscussionReply updateReply(DiscussionReply reply);

    // 删除回复（逻辑删除）
    boolean deleteReply(Long replyId, Long userId);

    // 获取主题的回复列表
    List<DiscussionReply> listRepliesByDiscussion(Long discussionId, Integer page, Integer size);

    // 标记最佳回复
    boolean markBestReply(Long discussionId, Long replyId, Long userId);

    // 点赞/取消点赞回复
    boolean toggleLikeReply(Long replyId, Long userId);
}