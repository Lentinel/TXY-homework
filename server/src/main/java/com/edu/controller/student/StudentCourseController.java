package com.edu.controller.student;

import com.edu.dto.*;
import com.edu.entity.Course;
import com.edu.result.PageResult;
import com.edu.result.Result;
import com.edu.service.CourseService;
import com.edu.service.LearningRecordService;
import com.edu.vo.CourseLearningVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("StudentCourseController")
@RequestMapping("/api/student/course")
@Slf4j
public class StudentCourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private LearningRecordService learningRecordService;
    @GetMapping("/categories")//OK
    public Result<PageResult> categories(@RequestBody CategoryPageQueryDTO categoryPageQueryDTO)
    {
        PageResult pageResult = courseService.categoryPageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }
    @GetMapping("/courses")//ok
    public Result<PageResult> page(@RequestBody CoursePageQueryDTO coursePageQueryDTO)
    {
        log.info("课程分页查询{}", coursePageQueryDTO);
        PageResult pageResult=courseService.pageQuery(coursePageQueryDTO);
        return Result.success(pageResult);
    }
    @GetMapping("/detail/{courseId}")//OK
    public Result<Course> courseDetails(@PathVariable("courseId") long courseId)
    {
        log.info("查询课程详情{}",courseId);
        Course course=courseService.detailQuery(courseId);
        return Result.success(course);
    }
    @GetMapping("/{id}/course")//ok
    public Result<PageResult> myCourses(@PathVariable("id")long id,
            @RequestBody CourseQueryPersonDTO courseQueryPersonDTO)
    {
        log.info("查询用户课程{}",courseQueryPersonDTO);
        courseQueryPersonDTO.setId(id);
        PageResult pageResult=courseService.personPageQuery(courseQueryPersonDTO);
        return Result.success(pageResult);
    }
    @PostMapping("/{courseId}/enroll/{userId}")//ok
    public Result<String> enroll(@PathVariable("courseId")long courseId,
                                 @PathVariable("userId")long userId)
    {
        log.info("用户加入课程");
        courseService.enroll(courseId,userId);
        return Result.success();
    }
    @GetMapping("/{courseId}/chapter")//ok
    public Result<PageResult> chapters(@RequestBody ChapterPageQueryDTO chapterPageQueryDTO)
    {
        log.info("查询课程章节{}",chapterPageQueryDTO);
        PageResult pageResult=courseService.chapters(chapterPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{courseId}/exams")
    public Result<PageResult> exams(@RequestBody ExamPageQueryDTO examPageQueryDTO)
    {
        log.info("查询课程考试信息{}",examPageQueryDTO);
        PageResult pageResult=courseService.examsQuery(examPageQueryDTO);
        return Result.success(pageResult);
    }
    /**
     * 查询学生某课程的学习记录（包含章节和视频进度）
     */
    @GetMapping("/course/record")
    public Result<CourseLearningVO> getCourseLearningRecord(
            @RequestParam Long userId,
            @RequestParam Long courseId
    ) {
        CourseLearningVO result = learningRecordService.getCourseLearningDetail(userId, courseId);
        return Result.success(result);
    }




}
