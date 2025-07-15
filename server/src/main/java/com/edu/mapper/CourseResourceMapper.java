package com.edu.mapper;

import com.edu.entity.CourseResource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseResourceMapper {
    // 插入课程资源
    void insert(CourseResource resource);

    // 根据课程ID查询资源列表
    List<CourseResource> selectByCourseId(Long courseId);

    // 根据ID查询资源
    CourseResource selectById(Long id);
}