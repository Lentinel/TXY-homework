package com.edu.entity;

import java.util.List;

public class Forum {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private Integer forumType;
    private Long categoryId;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private Boolean isTop;
    private Boolean isEssence;
    private Integer status;

    // 关联用户
    private User user;

    // 关联评论
    private List<ForumComment> comments;

    // Getters and Setters
}
