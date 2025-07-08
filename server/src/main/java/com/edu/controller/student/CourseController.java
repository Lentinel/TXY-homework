package com.edu.controller.student;

import com.edu.dto.*;
import com.edu.entity.Course;
import com.edu.result.PageResult;
import com.edu.result.Result;
import com.edu.service.CourseService;
import com.edu.vo.ChapterVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student/course")
@Slf4j
public class CourseController {
    @Autowired
    private CourseService courseService;
    @GetMapping("/page")
    public Result<PageResult> page(CourseQueryDTO courseQueryDTO)
    {
        log.info("课程分页查询{}",courseQueryDTO);
        PageResult pageResult=courseService.pageQuery(courseService);
        return Result.success(pageResult);
    }
    @GetMapping("/detail/{id}")
    public Result<Course> details(CourseDetailQueryDTO courseDetailQueryDTO)
    {
        log.info("查询课程详情{}",courseDetailQueryDTO);
        Course course=courseService.detailQuery(courseDetailQueryDTO);
        return Result.success(course);
    }
    @GetMapping("/{id}/course")
    public Result<PageResult> myCourses(CourseQueryPersonDTO courseQueryPersonDTO)
    {
        log.info("查询用户课程{}",courseQueryPersonDTO);
        PageResult pageResult=courseService.personPageQuery(courseQueryPersonDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{courseId}/chapter")
    public Result<PageResult> chapters(ChapterPageQueryDTO chapterPageQueryDTO)
    {
        log.info("查询课程章节{}",chapterPageQueryDTO);
        PageResult pageResult=courseService.chapters(chapterPageQueryDTO);
        return Result.success(pageResult);
    }
    @GetMapping("/{courseId}/chapter/{chapterId}")
    public Result<ChapterVO> chapter(ChapterDTO chapterDTO)
    {
        log.info("进入章节{}",chapterDTO);
        ChapterVO chapterVO=courseService.chapter(chapterDTO);
        return Result.success(chapterVO);
    }
    @GetMapping("/{courseId}/exams")
    public Result<PageResult> exams(ExamPageQueryDTO examPageQueryDTO)
    {
        log.info("查询课程考试信息{}",examPageQueryDTO);
        PageResult pageResult=courseService.examsQuery(examPageQueryDTO);
        return Result.success(pageResult);
    }






}
