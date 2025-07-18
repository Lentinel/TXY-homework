package com.edu.controller.teacher;

import com.edu.dto.*;
import com.edu.entity.*;
import com.edu.result.PageResult;
import com.edu.result.Result;
import com.edu.service.CourseService;
import com.edu.vo.ChapterVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("TeacherCourseController")
@RequestMapping("/api/teacher/course")
@Slf4j
public class TeacherCourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping
    public Result<String> createCourse(@RequestBody TeacherCourseDTO teacherCourseDTO)//ok
    {
        log.info("建立课程{}",teacherCourseDTO);
        Course course=new Course();
        BeanUtils.copyProperties(teacherCourseDTO,course);
        courseService.insertCategory(course);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<String> takeoffCourse(@PathVariable("id")long id)
    {
        log.info("下架课程{}",id);
        courseService.takeoffCourse(id);
        return Result.success();
    }
    @PutMapping("/{courseId}/desc")//ok?
    public Result<String> updateCourseDESC(@PathVariable("courseId")long courseId,
                                           @RequestParam("desc") String desc)
    {
        log.info("更新课程");
        Course course=new Course();
        course.setId(courseId);
        course.setDescription(desc);
        courseService.update(course);
        return Result.success();
    }
    @GetMapping("/{courseId}")//OK
    public Result<Course> getCourseDetail(@PathVariable("courseId")long courseId)
    {
        log.info("获取课程信息{}",courseId);
        Course course = courseService.detailQuery(courseId);
        return Result.success(course);

    }
    @GetMapping("/{instructorId}/page")//OK
    public Result<PageResult> coursePageQuery(@PathVariable("instructorId")long instructorId,
            @RequestBody TeachCoursePageQueryDTO teachCoursePageQueryDTO)
    {
        teachCoursePageQueryDTO.setInstructorId(instructorId);
        log.info("查询教师教授课程{}",teachCoursePageQueryDTO);

        PageResult pageResult=courseService.teachCoursePageQuery(teachCoursePageQueryDTO);
        return Result.success(pageResult);
    }
    @GetMapping("/{courseId}/chapter")//ok
    public Result<PageResult> chapters(ChapterPageQueryDTO chapterPageQueryDTO)
    {
        log.info("查询课程章节{}",chapterPageQueryDTO);
        PageResult pageResult=courseService.chapters(chapterPageQueryDTO);
        return Result.success(pageResult);
    }
    @GetMapping("/{courseId}/chapter/{chapterId}")//ok
    public Result<ChapterVO> chapter(@PathVariable("courseId")long courseId,@PathVariable("chapterId")long chapterId)
    {
        ChapterDTO chapterDTO = new ChapterDTO();
        chapterDTO.setChapterId(chapterId);
        chapterDTO.setCourseId(courseId);
        log.info("查询章节{}",chapterDTO);
        ChapterVO chapterVO=courseService.chapter(chapterDTO);
        return Result.success(chapterVO);
    }
    @PostMapping("/{courseId}/chapter")//ok
    public Result<String> createChapter(@RequestBody Chapter chapter)
    {
        courseService.createChapter(chapter);
        return Result.success();
    }
    @PostMapping("/{courseId}/chapter/{chapterId}")//OK
    public Result<String> update(@RequestBody ChapterUpdateDTO chapterUpdateDTO)
    {
        log.info("更新章节{}",chapterUpdateDTO);
        Chapter chapter=new Chapter();
        BeanUtils.copyProperties(chapterUpdateDTO,chapter);
        courseService.updateChapter(chapter);
        return Result.success();
    }
    @DeleteMapping("/{courseId}/chapter/{chapterId}")
    public Result<String> delete(@PathVariable("chapterId")long chapterId)
    {
        log.info("删除章节{}",chapterId);
        courseService.deleteChapter(chapterId);
        return Result.success();
    }
    @GetMapping("/{courseId}/exams")
    public Result<PageResult> examPageQuery(ExamPageQueryDTO examPageQueryDTO)
    {
        log.info("查询课程考试信息{}",examPageQueryDTO);
        PageResult pageResult=courseService.examsQuery(examPageQueryDTO);
        return Result.success(pageResult);
    }

    @PutMapping("/{courseId}/exam/{examId}/{userId}/question/{sortOrder}/grade")
    public Result<String> grade(@PathVariable("examId")long examId,
                                @PathVariable("sortOrder")long sortOrder,
                                @PathVariable("userId")long userId,
                                long score
                                )
    {
        courseService.grade(examId,sortOrder,userId,score);
        return Result.success();
    }
    @GetMapping("/{courseId}/exam/{examId}/records")
    public Result<PageResult> getRecords(@RequestBody RecordPageQueryDTO recordPageQueryDTO)
    {
        PageResult pageResult=courseService.RecordsPageQuery(recordPageQueryDTO);
        return Result.success(pageResult);
    }
    @GetMapping("/{courseId}/exam/{examId}/records/{recordId}")
    public Result<UserExamRecord> getRecord(@PathVariable("recordId")long recordId)
    {
        UserExamRecord userExamRecord=
         courseService.getRecord(recordId);
        return Result.success(userExamRecord);
    }
    @GetMapping("/{courseId}/student")
    public Result<PageResult> getStudents(@PathVariable("courseId")long courseId)
    {
        PageResult pageResult=courseService.getStudents(courseId);
        return Result.success(pageResult);
    }
    //TODO 非必要功能
    @GetMapping("/{courseId}/student/{studentId}")
    public Result courseStudentDetail()
    {

        return null;
    }
    //TODO 非必要功能
    @PostMapping("/{courseId}/notification")
    public Result<String> polishNotification()
    {
        return null;
    }
    //TODO 非必要功能
    @GetMapping("/{courseId}/notification")
    public Result notifications()
    {
        return null;
    }
    //TODO 非必要功能
    @GetMapping("/{courseId}/notification/{notificationId}")
    public Result notification()
    {
        return null;
    }
    //TODO 非必要功能
    @DeleteMapping("/{courseId}/notification/{notificationId}")
    public Result<String> deleteNotification()
    {
        return null;
    }
    //TODO 非必要功能
    @PutMapping("/{courseId}/notification/{notificationId}")
    public Result<String> editNotification()
    {
        return null;
    }
    @GetMapping("/{courseId}/statistics")
    public Result<CourseStatistics> getStatistics(@PathVariable("courseId")long courseId)
    {
        CourseStatistics courseStatistics=courseService.getStatistics(courseId);

        return null;
    }
}
