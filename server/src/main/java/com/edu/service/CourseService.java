package com.edu.service;

import com.edu.dto.*;
import com.edu.entity.*;
import com.edu.result.PageResult;
import com.edu.vo.CategoryVO;
import com.edu.vo.ChapterVO;
import com.edu.vo.CourseSaleVO;
import com.edu.vo.QuestionAnswerVO;

public interface CourseService {
    PageResult pageQuery(CoursePageQueryDTO coursePageQueryDTO);

    Course detailQuery(long id);

    PageResult personPageQuery(CourseQueryPersonDTO courseQueryPersonDTO);

    

    PageResult chapters(ChapterPageQueryDTO chapterPageQueryDTO);

    ChapterVO chapter(ChapterDTO chapterDTO);

    PageResult examsQuery(ExamPageQueryDTO examPageQueryDTO);

    void review(CourseReviewDTO courseReviewDTO);

    CourseSaleVO checkSales(CourseSaleDTO courseSaleDTO);

    CategoryVO getCategory(long id);

    void updateCategory(CourseCategoryUpdateDTO courseCategoryUpdateDTO);

    void createCategory(CreateCourseDTO createCourseDTO);

    void deleteCategory(long id);

    void update(Course course);

    void insertCategory(Course course);


    void takeoffCourse(long id);

    PageResult teachCoursePageQuery(TeachCoursePageQueryDTO teachCoursePageQueryDTO);

    void deleteCourse(long courseId);

    void updateChapter(Chapter chapter);

    void deleteChapter(long chapterId);

    Exam examQuery(long examId);

    PageResult categoryPageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void startExam(long examId, long userId);

    void endExam(long examId, long userId);

    void saveAns(QuestionAnswer questionAnswer);

    QuestionAnswerVO getQuestion(long sortOrder, long examId, long userId);

    void updateExam(ExamUpdateDTO examUpdateDTO);

    void deleteExam(long examId);

    void createExam(ExamDTO exam);

    Question getQuestion(long sortOrder, long examId);

    void createQuestion(CreateQuestionDTO createQuestionDTO);

    void deleteQuestion(long sortOrder, long examId);

    void updateQuestion(UpdateQuestionDTO updateQuestionDTO);

    void grade(long examId, long sortOrder, long userId, long score);

    PageResult RecordsPageQuery(RecordPageQueryDTO recordPageQueryDTO);

    UserExamRecord getRecord(long recordId);

    CourseStatistics getStatistics(long courseId);

    PageResult getStudents(long courseId);

    void createChapter(Chapter chapter);

    void enroll(long courseId, long userId);
}
