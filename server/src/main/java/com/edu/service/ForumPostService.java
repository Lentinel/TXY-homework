package com.edu.service;

import com.edu.entity.ForumPost;

import java.util.List;

public interface ForumPostService {
    // 发布帖子
    ForumPost createPost(ForumPost post);

    // 更新帖子
    ForumPost updatePost(ForumPost post);

    // 获取帖子详情（并增加浏览量）
    ForumPost getPostDetail(Long postId);

    // 分页查询板块帖子
    List<ForumPost> getPostsBySectionId(Long sectionId, Integer page, Integer size);

    // 获取热门帖子
    List<ForumPost> getHotPosts(Integer limit);

    // 帖子点赞/取消点赞
    boolean togglePostLike(Long postId, Long userId);
}

// 实现类
