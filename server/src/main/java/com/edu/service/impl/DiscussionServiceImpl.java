package com.edu.service.impl;

import com.edu.entity.CourseDiscussion;
import com.edu.mapper.CourseDiscussionMapper;
import com.edu.service.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiscussionServiceImpl implements DiscussionService {

    @Autowired
    private CourseDiscussionMapper discussionMapper;

    @Override
    @Transactional
    public CourseDiscussion createDiscussion(CourseDiscussion discussion) {
        // 设置默认值
        discussion.setStatus(1);
        discussion.setReplyCount(0);
        discussion.setViewCount(0);

        // 保存讨论主题
        discussionMapper.insert(discussion);

        // 返回带ID的讨论主题
        return discussion;
    }

    @Override
    public CourseDiscussion updateDiscussion(CourseDiscussion discussion) {
        // 检查讨论是否存在
        CourseDiscussion existingDiscussion = discussionMapper.selectById(discussion.getId());
        if (existingDiscussion == null) {
            throw new IllegalArgumentException("讨论主题不存在，ID: " + discussion.getId());
        }

        // 更新允许修改的字段
        if (discussion.getTitle() != null) {
            existingDiscussion.setTitle(discussion.getTitle());
        }
        if (discussion.getContent() != null) {
            existingDiscussion.setContent(discussion.getContent());
        }
        if (discussion.getType() != null) {
            existingDiscussion.setType(discussion.getType());
        }
        if (discussion.getStatus() != null) {
            existingDiscussion.setStatus(discussion.getStatus());
        }

        // 执行更新
        discussionMapper.update(existingDiscussion);

        return existingDiscussion;
    }

    @Override
    @Transactional
    public boolean deleteDiscussion(Long discussionId, Long userId) {
        // 检查是否存在且有权限删除
        CourseDiscussion discussion = discussionMapper.selectById(discussionId);
        if (discussion == null || !discussion.getAuthorId().equals(userId)) {
            return false;
        }

        // 逻辑删除（标记为已删除）
        discussion.setStatus(4);
        return discussionMapper.update(discussion) > 0;
    }

    @Override
    public CourseDiscussion getDiscussionDetail(Long discussionId) {
        // 获取讨论详情，包括作者信息
        return discussionMapper.selectDetailById(discussionId);
    }

    @Override
    public List<CourseDiscussion> listCourseDiscussions(Long courseId, Integer page, Integer size) {
        int offset = (page - 1) * size;
        return discussionMapper.selectByCourseId(courseId, offset, size);
    }

    @Override
    public List<CourseDiscussion> listGlobalDiscussions(Integer page, Integer size) {
        int offset = (page - 1) * size;
        return discussionMapper.selectGlobalDiscussions(offset, size);
    }
}