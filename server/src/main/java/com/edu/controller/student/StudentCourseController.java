package com.edu.controller.student;

import com.edu.dto.*;
import com.edu.entity.Course;
import com.edu.entity.Exam;
import com.edu.entity.QuestionAnswer;
import com.edu.result.PageResult;
import com.edu.result.Result;
import com.edu.service.CourseService;
import com.edu.vo.ChapterVO;
import com.edu.vo.QuestionAnswerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController("StudentCourseController")
@RequestMapping("/api/student/course")
@Slf4j
public class StudentCourseController {
    @Autowired
    private CourseService courseService;
    @GetMapping("/categories")
    public Result<PageResult> categories(CategoryPageQueryDTO categoryPageQueryDTO)
    {
        PageResult pageResult = courseService.categoryPageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }
    @GetMapping("/courses")
    public Result<PageResult> page(CoursePageQueryDTO coursePageQueryDTO)
    {
        log.info("课程分页查询{}", coursePageQueryDTO);
        PageResult pageResult=courseService.pageQuery(coursePageQueryDTO);
        return Result.success(pageResult);
    }
    @GetMapping("/detail/{courseId}")
    public Result<Course> courseDetails(long courseId)
    {
        log.info("查询课程详情{}",courseId);
        Course course=courseService.detailQuery(courseId);
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
    public Result<ChapterVO> enterChapter(ChapterDTO chapterDTO)
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
    @GetMapping("/{courseId}/exam/{examId}")
    public Result<Exam> examInfo(@PathVariable("examId")long examId)
    {
        Exam exam=courseService.examQuery(examId);
        return Result.success(exam);
    }
    @PostMapping("/{courseId}/exam/{examId}/start")
    public Result<String> enterExam(@PathVariable("examId")long examId,long userId)
    {

        courseService.startExam(examId,userId);
        return Result.success();
    }
    @PostMapping("/{courseId}/exam/{examId}/end/{userId}")
    public Result<String> endExam(@PathVariable("examId")long examId
            ,@PathVariable("userId")long userId)
    {
        courseService.endExam(examId,userId);
        return Result.success();
    }
    @PostMapping("/{userId}/exam/{examId}/answers/{questionId}")
    public Result<String> saveAns(@PathVariable("questionId")long questionId,
                                  @PathVariable("examId")long examId,
                                  @PathVariable("userId")long userId,
                                  String ans)
    {
        QuestionAnswer questionAnswer=new QuestionAnswer();
        questionAnswer.setQuestionId(questionId);
        questionAnswer.setExamId(examId);
        questionAnswer.setUserId(userId);
        questionAnswer.setUpdatedAt(LocalDateTime.now());
        courseService.saveAns(questionAnswer);
        return Result.success();
    }
    @GetMapping("/{userId}/exam/{examId}/answers/{sortOrder}")
    public Result<QuestionAnswerVO> getQuestion(@PathVariable("sortOrder")long sortOrder,
                                                @PathVariable("examId")long examId,
                                                @PathVariable("userId")long userId)
    {

        QuestionAnswerVO questionAnswerVO= courseService.getQuestion(sortOrder,examId,userId);
        return Result.success(questionAnswerVO);
    }



}
