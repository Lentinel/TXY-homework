package com.edu.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForumLike {
    private Long id;
    private Long postId;
    private Long replyId;
    private Long userId;
    private LocalDateTime createTime;
}