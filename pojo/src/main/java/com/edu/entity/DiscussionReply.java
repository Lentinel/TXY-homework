package com.edu.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionReply implements Serializable {
    private Long id;
    private Long discussionId;
    private Long parentId;
    private Long authorId;
    private String content;
    private Boolean isBest;
    private Integer likeCount;
    private Integer status;
    private LocalDateTime createTime;

    // 关联对象
    private User author;
    private CourseDiscussion discussion;
    private DiscussionReply parent;
}