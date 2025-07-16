package com.edu.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EnrollmentMapper {
    void deleteByCourseId(long courseId);
}
