package com.edu.mapper;

import com.edu.entity.ChapterProgress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChapterProgressMapper {
    // 根据用户和章节查询进度
    ChapterProgress selectByUserAndChapter(@Param("userId") Long userId, @Param("chapterId") Long chapterId);

    // 根据用户和课程查询所有章节进度
    List<ChapterProgress> selectByUserAndCourse(@Param("userId") Long userId, @Param("courseId") Long courseId);

    // 插入进度记录
    int insert(ChapterProgress progress);

    // 更新进度记录
    int update(ChapterProgress progress);

    // 统计用户已完成的章节数
    int countCompletedByUserAndCourse(@Param("userId") Long userId, @Param("courseId") Long courseId);

    // 统计课程总章节数
    int countTotalChapters(@Param("courseId") Long courseId);
}