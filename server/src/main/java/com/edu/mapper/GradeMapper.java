package com.edu.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GradeMapper {
    void deleteByCourseId(long courseId);
}
