package com.edu.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForumReply {
    private Long id;
    private Long postId;
    private Long parentId;
    private Long authorId;
    private String content;
    private Integer likeCount;
    private Integer status;
    private LocalDateTime createTime;

    // 关联对象
    private User author;
    private ForumReply parent; // 父回复
}