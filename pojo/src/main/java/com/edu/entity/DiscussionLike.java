package com.edu.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionLike implements Serializable {
    private Long id;
    private Long replyId;
    private Long userId;
    private LocalDateTime createTime;
}