package com.edu.service;

import com.edu.entity.CourseDiscussion;

import java.util.List;

public interface DiscussionService {
    // 创建讨论主题
    CourseDiscussion createDiscussion(CourseDiscussion discussion);

    // 更新讨论主题
    CourseDiscussion updateDiscussion(CourseDiscussion discussion);

    // 删除讨论主题（逻辑删除）
    boolean deleteDiscussion(Long discussionId, Long userId);

    // 获取讨论详情（含作者信息）
    CourseDiscussion getDiscussionDetail(Long discussionId);

    // 获取课程讨论列表
    List<CourseDiscussion> listCourseDiscussions(Long courseId, Integer page, Integer size);

    // 获取全局论坛列表
    List<CourseDiscussion> listGlobalDiscussions(Integer page, Integer size);
}