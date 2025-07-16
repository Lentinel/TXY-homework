package com.edu.mapper;

import com.edu.entity.ExamQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ExamQuestionMapper {
    void insert(ExamQuestion examQuestion);
    void deleteByExamId(Long examId);
    List<ExamQuestion> selectByExamId(Long examId);

    List<Integer> selectQuestionTypesByExamId(Long examId);
}