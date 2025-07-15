package com.edu.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForumPost {
    private Long id;
    private Long sectionId;
    private String title;
    private String content;
    private Long authorId;
    private Boolean isTop;
    private Boolean isEssence;
    private Integer viewCount;
    private Integer replyCount;
    private Integer likeCount;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 关联对象
    private User author;
    private ForumSection section;
    private ForumReply lastReply; // 最后回复
}