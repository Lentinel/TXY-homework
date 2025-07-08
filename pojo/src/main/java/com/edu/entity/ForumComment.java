package com.edu.entity;

import java.util.List;

public class ForumComment {
    private long id;
    private Long forumId;
    private Long userId;
    private Long parentId;
    private String content;
    private Integer likeCount;
    private Integer status;

    // 关联用户
    private User user;

    // 关联帖子
    private Forum forum;

    // 关联父评论
    private ForumComment parentComment;

    // 子评论
    private List<ForumComment> childComments;

    // Getters and Setters
}
