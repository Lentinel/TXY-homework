package com.edu.mapper;

import com.edu.entity.CourseDiscussion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseDiscussionMapper {
    // 插入讨论主题
    int insert(CourseDiscussion discussion);

    // 更新讨论主题
    int update(CourseDiscussion discussion);

    // 根据ID查询讨论主题
    CourseDiscussion selectById(Long id);

    // 获取讨论详情（含作者信息）
    CourseDiscussion selectDetailById(Long id);

    // 根据课程ID查询讨论列表
    List<CourseDiscussion> selectByCourseId(@Param("courseId") Long courseId,
                                            @Param("offset") Integer offset,
                                            @Param("size") Integer size);

    // 查询全局论坛列表
    List<CourseDiscussion> selectGlobalDiscussions(@Param("offset") Integer offset,
                                                   @Param("size") Integer size);

    // 增加回复数
    int incrementReplyCount(Long discussionId);
}