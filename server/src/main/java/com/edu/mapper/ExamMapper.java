package com.edu.mapper;

import com.edu.dto.ExamPageQueryDTO;
import com.edu.dto.RecordPageQueryDTO;
import com.edu.dto.UpdateQuestionDTO;
import com.edu.entity.Exam;
import com.edu.entity.Question;
import com.edu.entity.QuestionAnswer;
import com.edu.entity.UserExamRecord;
import com.github.pagehelper.Page;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ExamMapper {
    Page<Exam> pageQuery(ExamPageQueryDTO examPageQueryDTO);

    void deleteByCourseId(long courseId);

    void deleteByChapterId(long chapterId);

    Exam getById(long examId);

    UserExamRecord getRecord(long examId, long userId);

    void CreateRecord(UserExamRecord userExamRecord);



    void tryAgain(UserExamRecord userExamRecord);

    void updateRecord(UserExamRecord userExamRecord);

    QuestionAnswer getAnswer(long questionId, long userId);

    void creatAns(QuestionAnswer answer);

    void updateAns(QuestionAnswer questionAnswer);

    List<QuestionAnswer> getUserAnswers(long examId, long userId);

    long getSortOrder(long questionId, long examId);

    Question getQuestion(long examId, long sortOrder);
    //TODO 暂时没有更改的欲望
    void updateExam(Exam exam);

    void delete(long examId);

    void createExam(Exam exam);

    long createQuestion(Question question, long examId, int sortOrder);

    void reSortQuestion(long sortOrder, long examId);

    void deleteQuestion(long sortOrder, long examId);

    void updateQuestion(UpdateQuestionDTO updateQuestionDTO);

    void updateQuestionAnswer(long examId, long sortOrder, long userId, long score);

    Page<UserExamRecord> recordPageQuery(RecordPageQueryDTO recordPageQueryDTO);

    UserExamRecord getRecordById(long recordId);

    void createQuestionLink(long id, long examId, int sortOrder, Integer score);

    BigDecimal getRecordScore(long recordId);

    void insert(Exam exam);
    void update(Exam exam);
    Exam selectById(Long id);
    List<Exam> selectByCourseId(Long courseId);
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
