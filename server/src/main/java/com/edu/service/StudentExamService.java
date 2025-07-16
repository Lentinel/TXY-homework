package com.edu.service;

import com.edu.vo.ExamResultVO;
import com.edu.vo.ExamVO;
import com.edu.vo.SubmitAnswersDTO;
import com.edu.vo.UserExamRecordVO;
import com.github.pagehelper.Page;

// StudentExamService.java
public interface StudentExamService {
    Page<ExamVO> getAvailableExams(Long courseId, Integer page, Integer pageSize);
    UserExamRecordVO startExam(Long examId, Long userId);
    void submitExam(Long recordId, SubmitAnswersDTO answersDTO);
    ExamResultVO getExamResult(Long recordId);
}
