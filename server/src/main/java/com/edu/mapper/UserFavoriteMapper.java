package com.edu.mapper;

import com.edu.entity.UserFavorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserFavoriteMapper {
    // 添加收藏
    int insert(UserFavorite favorite);

    // 取消收藏（逻辑删除）
    int markAsDeleted(@Param("userId") Long userId, @Param("courseId") Long courseId);

    // 检查是否已收藏
    UserFavorite selectByUserAndCourse(@Param("userId") Long userId, @Param("courseId") Long courseId);

    // 获取用户收藏的课程列表
    List<UserFavorite> selectCoursesByUser(@Param("userId") Long userId, @Param("offset") Integer offset, @Param("size") Integer size);

    // 统计用户收藏的课程数量
    int countCoursesByUser(@Param("userId") Long userId);
}