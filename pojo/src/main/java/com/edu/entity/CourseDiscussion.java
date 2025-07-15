package com.edu.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDiscussion implements Serializable {
    private Long id;
    private Long courseId;
    private Long authorId;
    private String title;
    private String content;
    private Integer type;
    private Integer status;
    private Integer replyCount;
    private Integer viewCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 关联对象
    private User author;
    private Course course;
}