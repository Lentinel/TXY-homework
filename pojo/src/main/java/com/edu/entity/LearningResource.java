package com.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class LearningResource {
    private Long id;
    private Integer resourceType;
    private String title;
    private String description;
    private String url;
    private Long size;
    private Integer duration;
    private Integer sortOrder;
    private Long uploaderId;
    private Long courseId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}