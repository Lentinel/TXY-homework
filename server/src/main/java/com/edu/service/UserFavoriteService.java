package com.edu.service;

import com.edu.entity.UserFavorite;

import java.util.List;

public interface UserFavoriteService {
    // 添加课程收藏
    UserFavorite addCourseFavorite(Long userId, Long courseId);

    // 取消课程收藏
    boolean removeCourseFavorite(Long userId, Long courseId);

    // 检查是否已收藏课程
    boolean isCourseFavorited(Long userId, Long courseId);

    // 获取用户收藏的课程列表
    List<UserFavorite> getFavoriteCourses(Long userId, Integer page, Integer size);

    // 获取用户收藏课程数量
    int getFavoriteCoursesCount(Long userId);
}
