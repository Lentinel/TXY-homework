package com.edu.service.impl;


import com.edu.entity.UserFavorite;
import com.edu.mapper.UserFavoriteMapper;
import com.edu.service.UserFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

// 实现类
@Service
public class UserFavoriteServiceImpl implements UserFavoriteService {
    @Autowired
    private UserFavoriteMapper favoriteMapper;

    @Transactional
    @Override
    public UserFavorite addCourseFavorite(Long userId, Long courseId) {
        // 检查是否已收藏
        UserFavorite existing = favoriteMapper.selectByUserAndCourse(userId, courseId);
        if (existing != null) {
            return existing;
        }

        // 创建新收藏
        UserFavorite favorite = UserFavorite.builder()
                .userId(userId)
                .courseId(courseId)
                .favoriteTime(LocalDateTime.now())
                .isDeleted(false)
                .build();

        favoriteMapper.insert(favorite);
        return favorite;
    }

    @Transactional
    @Override
    public boolean removeCourseFavorite(Long userId, Long courseId) {
        int rows = favoriteMapper.markAsDeleted(userId, courseId);
        return rows > 0;
    }

    @Override
    public boolean isCourseFavorited(Long userId, Long courseId) {
        UserFavorite favorite = favoriteMapper.selectByUserAndCourse(userId, courseId);
        return favorite != null;
    }

    @Override
    public List<UserFavorite> getFavoriteCourses(Long userId, Integer page, Integer size) {
        int offset = (page - 1) * size;
        return favoriteMapper.selectCoursesByUser(userId, offset, size);
    }

    @Override
    public int getFavoriteCoursesCount(Long userId) {
        return favoriteMapper.countCoursesByUser(userId);
    }
}