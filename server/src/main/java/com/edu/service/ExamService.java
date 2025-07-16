package com.edu.service;

import com.edu.entity.Exam;
import com.edu.entity.ExamQuestion;
import com.edu.vo.ExamWithQuestionsVO;
import com.edu.vo.ReviewDTO;
import com.edu.vo.UserExamRecordVO;
import com.github.pagehelper.Page;

import java.util.List;

// ExamService.java
public interface ExamService {
    Long createExam(Exam exam);
    void updateExam(Exam exam);
    void addQuestions(Long examId, List<ExamQuestion> questions);
    void updateStatus(Long examId, Integer status);
    ExamWithQuestionsVO getExamWithQuestions(Long examId);
    Page<UserExamRecordVO> getExamRecords(Long examId, Integer page, Integer pageSize);
    void reviewRecord(Long recordId, ReviewDTO reviewDTO);
}
