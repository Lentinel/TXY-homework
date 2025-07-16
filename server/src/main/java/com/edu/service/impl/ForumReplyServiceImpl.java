package com.edu.service.impl;

import com.edu.entity.ForumLike;
import com.edu.entity.ForumReply;
import com.edu.mapper.ForumLikeMapper;
import com.edu.mapper.ForumPostMapper;
import com.edu.mapper.ForumReplyMapper;
import com.edu.service.ForumReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ForumReplyServiceImpl implements ForumReplyService {
    @Autowired
    private ForumReplyMapper replyMapper;
    @Autowired
    private ForumPostMapper postMapper;
    @Autowired
    private ForumLikeMapper likeMapper;

    @Transactional
    @Override
    public ForumReply createReply(ForumReply reply) {
        // 保存回复
        replyMapper.insert(reply);
        // 更新帖子回复计数
        postMapper.incrementReplyCount(reply.getPostId());
        return reply;
    }

    @Override
    public List<ForumReply> getRepliesByPostId(Long postId, Integer page, Integer size) {
        int offset = (page - 1) * size;
        return replyMapper.selectByPostId(postId, offset, size);
    }

    @Transactional
    @Override
    public boolean toggleReplyLike(Long replyId, Long userId) {
        ForumLike like = likeMapper.selectByReplyAndUser(replyId, userId);
        if (like != null) {
            // 取消点赞
            likeMapper.deleteByReplyAndUser(replyId, userId);
            replyMapper.decrementLikeCount(replyId);
            return false;
        } else {
            // 新增点赞
            likeMapper.insert(ForumLike.builder()
                    .replyId(replyId)
                    .userId(userId)
                    .build());
            replyMapper.incrementLikeCount(replyId);
            return true;
        }
    }
}