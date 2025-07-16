package com.edu.controller.student;

import com.edu.result.PageResult;
import com.edu.result.Result;
import com.edu.service.StudentExamService;
import com.edu.vo.ExamResultVO;
import com.edu.vo.ExamVO;
import com.edu.vo.SubmitAnswersDTO;
import com.edu.vo.UserExamRecordVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student/exams")
public class StudentExamController {
    @Autowired
    private StudentExamService studentExamService;

    // 获取可参加的考试列表
    @GetMapping
    public Result<PageResult> getAvailableExams(
            @RequestParam Long courseId,
            @RequestParam Integer page,
            @RequestParam Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        Page<ExamVO> exams = studentExamService.getAvailableExams(courseId, page, pageSize);
        return Result.success(new PageResult(exams.getTotal(),exams.getResult()));
    }

    // 开始考试（创建记录）
    @PostMapping("/{examId}/start")
    public Result<UserExamRecordVO> startExam(@PathVariable Long examId, @RequestParam Long userId) {
        UserExamRecordVO record = studentExamService.startExam(examId, userId);
        return Result.success(record);
    }

    // 提交考试答案
    @PostMapping("/records/{recordId}/submit")
    public Result<Void> submitExam(
            @PathVariable Long recordId,
            @RequestBody SubmitAnswersDTO answersDTO) {
        studentExamService.submitExam(recordId, answersDTO);
        return Result.success();
    }

    // 查看考试结果
    @GetMapping("/records/{recordId}")
    public Result<ExamResultVO> getExamResult(@PathVariable Long recordId) {
        ExamResultVO result = studentExamService.getExamResult(recordId);
        return Result.success(result);
    }
}