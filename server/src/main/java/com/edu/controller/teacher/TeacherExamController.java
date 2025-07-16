package com.edu.controller.teacher;

import com.edu.entity.Exam;
import com.edu.entity.ExamQuestion;
import com.edu.result.PageResult;
import com.edu.result.Result;
import com.edu.service.ExamService;
import com.edu.vo.ExamWithQuestionsVO;
import com.edu.vo.ReviewDTO;
import com.edu.vo.UserExamRecordVO;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/exams")
public class TeacherExamController {
    @Autowired
    private ExamService examService;

    // 创建考试
    @PostMapping
    public Result<Long> createExam(@RequestBody Exam exam) {
        Long examId = examService.createExam(exam);
        return Result.success(examId);
    }

    // 更新考试基本信息
    @PutMapping("/{id}")
    public Result<Void> updateExam(@PathVariable Long id, @RequestBody Exam exam) {
        exam.setId(id);
        examService.updateExam(exam);
        return Result.success();
    }

    // 向考试添加试题
    @PostMapping("/{examId}/questions")
    public Result<Void> addQuestions(@PathVariable Long examId, @RequestBody List<ExamQuestion> questions) {
        examService.addQuestions(examId, questions);
        return Result.success();
    }

    // 发布考试
    @PutMapping("/{id}/publish")
    public Result<Void> publishExam(@PathVariable Long id) {
        examService.updateStatus(id, 2); // 状态改为"已发布"
        return Result.success();
    }

    // 获取考试详情（含试题）
    @GetMapping("/{id}")
    public Result<ExamWithQuestionsVO> getExamDetail(@PathVariable Long id) {
        ExamWithQuestionsVO detail = examService.getExamWithQuestions(id);
        return Result.success(detail);
    }

    // 查看学生考试记录（待批改）
    @GetMapping("/{examId}/records")
    public Result<PageResult> getExamRecords(
            @PathVariable Long examId,
            @RequestParam Integer page,
            @RequestParam Integer pageSize) {
        Page<UserExamRecordVO> records = examService.getExamRecords(examId, page, pageSize);
        return Result.success(new PageResult(records.getTotal(),records.getResult()));
    }

    // 批改试卷
    @PutMapping("/records/{recordId}/review")
    public Result<Void> reviewRecord(
            @PathVariable Long recordId,
            @RequestBody ReviewDTO reviewDTO) {
        examService.reviewRecord(recordId, reviewDTO);
        return Result.success();
    }
}