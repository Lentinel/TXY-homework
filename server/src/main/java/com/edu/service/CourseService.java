package com.edu.service;

import com.edu.dto.*;
import com.edu.entity.Course;
import com.edu.result.PageResult;
import com.edu.vo.ChapterVO;

public interface CourseService {
    PageResult pageQuery(CourseService courseService);

    Course detailQuery(CourseDetailQueryDTO courseDetailQueryDTO);

    PageResult personPageQuery(CourseQueryPersonDTO courseQueryPersonDTO);

    

    PageResult chapters(ChapterPageQueryDTO chapterPageQueryDTO);

    ChapterVO chapter(ChapterDTO chapterDTO);

    PageResult examsQuery(ExamPageQueryDTO examPageQueryDTO);
}
