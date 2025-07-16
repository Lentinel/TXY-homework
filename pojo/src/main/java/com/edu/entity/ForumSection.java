package com.edu.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForumSection {
    private Long id;
    private String name;
    private String description;
    private String icon;
    private Integer sortOrder;
    private Integer status;
    private Integer postCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}