package com.edu.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFavorite {
    private Long id;
    private Long userId;
    private Long courseId;
    private LocalDateTime favoriteTime;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 关联对象
    private User user;
    private Course course; // 需要自行定义Course实体
}