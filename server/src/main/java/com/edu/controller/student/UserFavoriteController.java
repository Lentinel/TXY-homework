package com.edu.controller.student;

import com.edu.result.Result;
import com.edu.entity.UserFavorite;
import com.edu.service.UserFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class UserFavoriteController {
    @Autowired
    private UserFavoriteService favoriteService;

    // 添加课程收藏
    @PostMapping("/courses/{courseId}")
    public Result<UserFavorite> addCourseFavorite(
            @RequestParam Long userId,
            @PathVariable Long courseId) {
        return Result.success(favoriteService.addCourseFavorite(userId, courseId));
    }

    // 取消课程收藏
    @DeleteMapping("/courses/{courseId}")
    public Result<Boolean> removeCourseFavorite(
            @RequestParam Long userId,
            @PathVariable Long courseId) {
        return Result.success(favoriteService.removeCourseFavorite(userId, courseId));
    }

    // 检查是否已收藏课程
    @GetMapping("/courses/{courseId}/check")
    public Result<Boolean> checkCourseFavorite(
            @RequestParam Long userId,
            @PathVariable Long courseId) {
        return Result.success(favoriteService.isCourseFavorited(userId, courseId));
    }

    // 获取用户收藏的课程列表
    @GetMapping("/courses")
    public Result<List<UserFavorite>> getFavoriteCourses(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        return Result.success(favoriteService.getFavoriteCourses(userId, page, size));
    }

    // 获取用户收藏课程数量
    @GetMapping("/courses/count")
    public Result<Integer> getFavoriteCoursesCount(@RequestParam Long userId) {
        return Result.success(favoriteService.getFavoriteCoursesCount(userId));
    }
}