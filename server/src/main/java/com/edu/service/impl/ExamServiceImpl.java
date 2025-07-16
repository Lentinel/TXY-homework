package com.edu.service.impl;

import com.edu.entity.Exam;
import com.edu.entity.ExamQuestion;
import com.edu.entity.Question;
import com.edu.entity.UserExamRecord;
import com.edu.mapper.ExamMapper;
import com.edu.mapper.ExamQuestionMapper;
import com.edu.mapper.QuestionMapper;
import com.edu.mapper.UserExamRecordMapper;
import com.edu.service.ExamService;
import com.edu.vo.ExamWithQuestionsVO;
import com.edu.vo.ReviewDTO;
import com.edu.vo.UserExamRecordVO;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

// ExamServiceImpl.java
@Service
@Transactional
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private ExamQuestionMapper examQuestionMapper;
    @Autowired
    private UserExamRecordMapper recordMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public Long createExam(Exam exam) {
        exam.setStatus(1); // 默认草稿状态
        examMapper.insert(exam);
        return exam.getId();
    }

    @Override
    public void updateExam(Exam exam) {

    }

    @Override
    public void addQuestions(Long examId, List<ExamQuestion> questions) {
        // 先删除原有试题关联（可选）
        examQuestionMapper.deleteByExamId(examId);
        // 批量插入新关联
        questions.forEach(q -> {
            q.setExamId(examId);
            examQuestionMapper.insert(q);
        });
    }

    @Override
    public void updateStatus(Long examId, Integer status) {

    }

    @Override
    public ExamWithQuestionsVO getExamWithQuestions(Long examId) {
        Exam exam = examMapper.selectById(examId);
        List<ExamQuestion> questions = examQuestionMapper.selectByExamId(examId);
        // 关联查询试题详情
        questions.forEach(q -> {
            Question question = questionMapper.selectById(q.getQuestionId());
            q.setQuestion(question);
        });
        return new ExamWithQuestionsVO(exam, questions);
    }

    @Override
    public Page<UserExamRecordVO> getExamRecords(Long examId, Integer page, Integer pageSize) {
        return null;
    }


    @Override
    public void reviewRecord(Long recordId, ReviewDTO reviewDTO) {
        // 1. 查询考试记录
        UserExamRecord record = recordMapper.selectById(recordId);
        if (record == null) {
            throw new RuntimeException("考试记录不存在");
        }

        // 2. 通过 examId 查询考试信息（获取及格分）
        Exam exam = examMapper.selectById(record.getExamId());
        if (exam == null) {
            throw new RuntimeException("考试信息不存在");
        }

        // 3. 判断是否通过（使用查询到的 exam 而非 record.getExam()）
        int isPassed = reviewDTO.getTotalScore().compareTo(new BigDecimal(exam.getPassingScore())) >= 0 ? 1 : 0;

        // 4. 更新记录
        record.setScore(reviewDTO.getTotalScore());
        record.setReview(reviewDTO.getReviewContent());
        record.setIsPassed(isPassed);
        record.setStatus(3); // 已批改
        recordMapper.update(record);
    }

    // 其他方法实现...
}