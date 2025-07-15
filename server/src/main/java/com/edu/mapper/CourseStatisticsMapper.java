package com.edu.mapper;

import com.edu.entity.CourseStatistics;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseStatisticsMapper {
    void deleteByCourseId(long courseId);

    CourseStatistics get(long courseId);
}
