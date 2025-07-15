package com.edu.mapper;

import com.edu.dto.CoursePageQueryDTO;
import com.edu.dto.CourseQueryPersonDTO;
import com.edu.entity.Course;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper {

    Page<Course> pageQuery(CoursePageQueryDTO coursePageQueryDTO);

    Course query(long id);

    Page<Course> personPageQuery(CourseQueryPersonDTO courseQueryPersonDTO);

    void update(Course course);

    void deleteCategory(long id);

    void insert(Course course);

    void delete(long courseId);
}
