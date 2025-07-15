package com.edu.service.impl;

import com.edu.entity.CourseDiscussion;
import com.edu.entity.DiscussionLike;
import com.edu.entity.DiscussionReply;
import com.edu.mapper.CourseDiscussionMapper;
import com.edu.mapper.DiscussionLikeMapper;
import com.edu.mapper.DiscussionReplyMapper;
import com.edu.service.DiscussionReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiscussionReplyServiceImpl implements DiscussionReplyService {

    @Autowired
    private DiscussionReplyMapper replyMapper;

    @Autowired
    private DiscussionLikeMapper likeMapper;

    @Autowired
    private CourseDiscussionMapper discussionMapper;

    @Override
    @Transactional
    public DiscussionReply createReply(DiscussionReply reply) {
        // 设置默认值
        reply.setStatus(1);
        reply.setLikeCount(0);
        reply.setIsBest(false);

        // 保存回复
        replyMapper.insert(reply);

        // 更新讨论的回复数
        discussionMapper.incrementReplyCount(reply.getDiscussionId());

        return reply;
    }

    @Override
    public DiscussionReply updateReply(DiscussionReply reply) {
        // 检查回复是否存在
        DiscussionReply existingReply = replyMapper.selectById(reply.getId());
        if (existingReply == null) {
            throw new IllegalArgumentException("回复不存在，ID: " + reply.getId());
        }

        // 更新允许修改的字段
        if (reply.getContent() != null) {
            existingReply.setContent(reply.getContent());
        }
        if (reply.getStatus() != null) {
            existingReply.setStatus(reply.getStatus());
        }

        // 执行更新
        replyMapper.update(existingReply);

        return existingReply;
    }
    @Override
    @Transactional
    public boolean deleteReply(Long replyId, Long userId) {
        // 检查是否存在且有权限删除
        DiscussionReply reply = replyMapper.selectById(replyId);
        if (reply == null || !reply.getAuthorId().equals(userId)) {
            return false;
        }

        // 逻辑删除（标记为已删除）
        reply.setStatus(2);
        return replyMapper.update(reply) > 0;
    }

    @Override
    public List<DiscussionReply> listRepliesByDiscussion(Long discussionId, Integer page, Integer size) {
        int offset = (page - 1) * size;
        return replyMapper.selectByDiscussionId(discussionId, offset, size);
    }

    @Override
    @Transactional
    public boolean markBestReply(Long discussionId, Long replyId, Long userId) {
        // 检查用户是否为讨论主题的作者
        CourseDiscussion discussion = discussionMapper.selectById(discussionId);
        if (discussion == null || !discussion.getAuthorId().equals(userId)) {
            return false;
        }

        // 先取消该讨论下所有回复的最佳标记
        replyMapper.cancelBestReply(discussionId);

        // 标记指定回复为最佳
        return replyMapper.markBestReply(replyId) > 0;
    }

    @Override
    @Transactional
    public boolean toggleLikeReply(Long replyId, Long userId) {
        // 检查是否已点赞
        DiscussionLike existingLike = likeMapper.selectByReplyAndUser(replyId, userId);

        if (existingLike != null) {
            // 已点赞，取消点赞
            likeMapper.delete(existingLike.getId());
            replyMapper.decrementLikeCount(replyId);
            return false;
        } else {
            // 未点赞，添加点赞
            DiscussionLike like = DiscussionLike.builder()
                    .replyId(replyId)
                    .userId(userId)
                    .build();
            likeMapper.insert(like);
            replyMapper.incrementLikeCount(replyId);
            return true;
        }
    }
}