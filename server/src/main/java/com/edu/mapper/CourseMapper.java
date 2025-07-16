package com.edu.mapper;

import com.edu.dto.CoursePageQueryDTO;
import com.edu.dto.CourseQueryPersonDTO;
import com.edu.dto.TeachCoursePageQueryDTO;
import com.edu.entity.Course;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface CourseMapper {

    Page<Course> pageQuery(CoursePageQueryDTO coursePageQueryDTO);

    Course query(long id);

    Page<Course> personPageQuery(CourseQueryPersonDTO courseQueryPersonDTO);

    void update(Course course);

    void deleteCategory(long id);

    void insert(Course course);

    void delete(long courseId);

    void enroll(long courseId, long userId, LocalDateTime localDateTime);

    Long getEnroll(long courseId, long userId);

    Page<Course> TeacherPageQuery(TeachCoursePageQueryDTO teachCoursePageQueryDTO);
}
