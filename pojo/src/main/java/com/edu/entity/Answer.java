package com.edu.entity;

public class Answer {
    private Long Id;
    private Long questionId;     // 问题ID
    private Long userId;         // 回答者ID
    private String content;      // 回答内容
    private Boolean isBest;      // 是否最佳回答
    private Integer likeCount;   // 点赞数
    private Integer status;      // 状态：1-正常，2-已删除，3-已屏蔽

    // 关联关系
    private User user;           // 回答者
    private QuestionAnswer question;  // 关联问题

}
