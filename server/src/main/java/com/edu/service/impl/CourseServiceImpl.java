package com.edu.service.impl;

import com.edu.dto.*;
import com.edu.entity.Course;
import com.edu.result.PageResult;
import com.edu.service.CourseService;
import com.edu.vo.ChapterVO;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    @Override
    public PageResult pageQuery(CourseService courseService) {
        return null;
    }

    @Override
    public Course detailQuery(CourseDetailQueryDTO courseDetailQueryDTO) {
        return null;
    }

    @Override
    public PageResult personPageQuery(CourseQueryPersonDTO courseQueryPersonDTO) {
        return null;
    }

    @Override
    public PageResult chapters(ChapterPageQueryDTO chapterPageQueryDTO) {
        return null;
    }

    @Override
    public ChapterVO chapter(ChapterDTO chapterDTO) {
        return null;
    }

    @Override
    public PageResult examsQuery(ExamPageQueryDTO examPageQueryDTO) {
        return null;
    }
}
