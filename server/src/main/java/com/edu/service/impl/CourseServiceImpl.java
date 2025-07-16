package com.edu.service.impl;

import com.edu.Exception.UserExamException;
import com.edu.constant.MessageConstant;
import com.edu.dto.*;
import com.edu.entity.*;
import com.edu.mapper.*;
import com.edu.result.PageResult;
import com.edu.service.CourseService;
import com.edu.vo.CategoryVO;
import com.edu.vo.ChapterVO;
import com.edu.vo.CourseSaleVO;
import com.edu.vo.QuestionAnswerVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private EnrollmentMapper enrollmentMapper;
    @Autowired
    private GradeMapper gradeMapper;
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private CourseStatisticsMapper courseStatisticsMapper;
    @Override
    public PageResult pageQuery(CoursePageQueryDTO coursePageQueryDTO) {
        PageHelper.startPage(coursePageQueryDTO.getPage(), coursePageQueryDTO.getPageSize());
        Page<Course> page=courseMapper.pageQuery(coursePageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Course detailQuery(long id) {

        Course course= courseMapper.query(id);
        return course;
    }

    @Override
    public PageResult personPageQuery(CourseQueryPersonDTO courseQueryPersonDTO) {
        PageHelper.startPage(courseQueryPersonDTO.getPage(),courseQueryPersonDTO.getPageSize());
        Page<Course> page = courseMapper.personPageQuery(courseQueryPersonDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public PageResult chapters(ChapterPageQueryDTO chapterPageQueryDTO) {
        PageHelper.startPage(chapterPageQueryDTO.getPage(),chapterPageQueryDTO.getPageSize());
        Page<Chapter> page =chapterMapper.pageQuery(chapterPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public ChapterVO chapter(ChapterDTO chapterDTO) {
        Chapter chapter=chapterMapper.query(chapterDTO);
        ChapterVO chapterVO=new ChapterVO();
        BeanUtils.copyProperties(chapter,chapterVO);
        return chapterVO;
    }

    @Override
    public PageResult examsQuery(ExamPageQueryDTO examPageQueryDTO) {
        PageHelper.startPage(examPageQueryDTO.getPage(),examPageQueryDTO.getPageSize());
        Page<Exam> page =  examMapper.pageQuery(examPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void review(CourseReviewDTO courseReviewDTO) {
        Course course = new Course();
        BeanUtils.copyProperties(courseReviewDTO,course);
        courseMapper.update(course);
    }
    //TODO 非必要功能
    @Override
    public CourseSaleVO checkSales(CourseSaleDTO courseSaleDTO) {
        return null;
    }

    @Override
    public CategoryVO getCategory(long id) {
        Category category= categoryMapper.getById(id);
        CategoryVO categoryVO=new CategoryVO();
        BeanUtils.copyProperties(category,categoryVO);
        if(categoryVO.getParent_id()!=0) {
            categoryVO.setParentName(getCategory(categoryVO.getParent_id()).getName());
        }
        return categoryVO;
    }

    @Override
    public void updateCategory(CourseCategoryUpdateDTO courseCategoryUpdateDTO) {
        Category category=new Category();
        category.setUpdateTime(LocalDateTime.now());
        BeanUtils.copyProperties(courseCategoryUpdateDTO,category);
        categoryMapper.update(category);
    }

    @Override
    public void createCategory(CreateCourseDTO createCourseDTO) {
        Category category=new Category();
        BeanUtils.copyProperties(createCourseDTO,category);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(category.getCreateTime());
        categoryMapper.insert(category);
    }

    @Override
    public void deleteCategory(long id) {
        courseMapper.deleteCategory(id);
        categoryMapper.deleteCategory(id);
        categoryMapper.deleteParentId(id);
    }

    @Override
    public void update(Course course) {
        course.setUpdateTime(LocalDateTime.now());
        courseMapper.update(course);
    }

    @Override
    public void insertCategory(Course course) {
        courseMapper.insert(course);

    }

    @Override
    public void takeoffCourse(long id) {

    }

    @Override
    public PageResult teachCoursePageQuery(TeachCoursePageQueryDTO teachCoursePageQueryDTO) {

        return null;
    }

    @Override
    public void deleteCourse(long courseId) {
        courseMapper.delete(courseId);
        examMapper.deleteByCourseId(courseId);
        chapterMapper.deleteByCourseId(courseId);
        courseStatisticsMapper.deleteByCourseId(courseId);
        gradeMapper.deleteByCourseId(courseId);
        enrollmentMapper.deleteByCourseId(courseId);
        reviewMapper.deleteByCourseId(courseId);

    }

    @Override
    public void updateChapter(Chapter chapter) {
        chapter.setUpdateTime(LocalDateTime.now());
        chapterMapper.update(chapter);
    }

    @Override
    public void deleteChapter(long chapterId) {
        examMapper.deleteByChapterId(chapterId);
        chapterMapper.delete(chapterId);
    }

    @Override
    public Exam examQuery(long examId) {
        Exam exam=examMapper.getById(examId);
        return exam;
    }

    @Override
    public PageResult categoryPageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page=categoryMapper.pageQuery(categoryPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void startExam(long examId, long userId) {
        UserExamRecord userExamRecord=examMapper.getRecord(examId,userId);
        Exam exam=examMapper.getById(examId);
        if (userExamRecord==null)
        {
            userExamRecord.setExamId(examId);
            userExamRecord.setUserId(userId);
            userExamRecord.setCourseId(exam.getCourseId());
            userExamRecord.setAttemptCount(1);
            userExamRecord.setStartTime(LocalDateTime.now());
            userExamRecord.setStatus(1);
            examMapper.CreateRecord(userExamRecord);
        }
        else if(userExamRecord.getStatus()==1)
        {
            throw new UserExamException(MessageConstant.IS_IN_EXAM);
        }
        else
        {
            if(userExamRecord.getAttemptCount()<exam.getMaxAttempts())
            {
                userExamRecord.setAttemptCount(userExamRecord.getAttemptCount()+1);
                userExamRecord.setStartTime(LocalDateTime.now());
                userExamRecord.setStatus(1);
                examMapper.tryAgain(userExamRecord);
            }
            else {
                throw new UserExamException(MessageConstant.HAVE_DONE_EXAM);
            }
        }
    }

    @Override
    public void endExam(long examId, long userId) {
            List<QuestionAnswer> answers= examMapper.getUserAnswers(examId,userId);
            UserExamRecord userExamRecord=examMapper.getRecord(examId,userId);
            userExamRecord.setStatus(2);
            userExamRecord.setSubmitTime(LocalDateTime.now());
            userExamRecord.setAnswers(answers);
            examMapper.updateRecord(userExamRecord);
    }

    @Override
    public void saveAns(QuestionAnswer questionAnswer) {
        Exam byId = examMapper.getById(questionAnswer.getExamId());
        UserExamRecord userExamRecord=examMapper.getRecord(questionAnswer.getExamId(),questionAnswer.getUserId());
        Duration duration=Duration.between(userExamRecord.getStartTime(), LocalDateTime.now());
        questionAnswer.setSortOrder(examMapper.getSortOrder(questionAnswer.getQuestionId(),questionAnswer.getExamId()));
        if(userExamRecord.getStatus()!=1||duration.toMinutes()>=byId.getDuration())
        {
            throw new RuntimeException("考试已结束");
        }
        QuestionAnswer answer = examMapper.getAnswer(questionAnswer.getQuestionId(),questionAnswer.getUserId());
        if(answer==null)
        {
            answer=new QuestionAnswer();
            BeanUtils.copyProperties(questionAnswer,answer);
            examMapper.creatAns(answer);
        }
        else
        {
            questionAnswer.setCreatedAt(LocalDateTime.now());
            questionAnswer.setCreatedAt(LocalDateTime.now());
            examMapper.updateAns(questionAnswer);
        }

    }

    @Override
    public QuestionAnswerVO getQuestion(long sortOrder, long examId, long userId) {

        UserExamRecord record = examMapper.getRecord(examId, userId);
        QuestionAnswerVO questionAnswerVO=new QuestionAnswerVO();
        Question question =examMapper.getQuestion(examId,sortOrder);
        if(record.getStatus()==1)
        {
            BeanUtils.copyProperties(question,questionAnswerVO);
            questionAnswerVO.setRightAnswer("");

            questionAnswerVO.setAnswer(record.getAnswers().get((int) sortOrder));
        }
        else
        {
            BeanUtils.copyProperties(question,questionAnswerVO);
            questionAnswerVO.setAnswer(record.getAnswers().get((int) sortOrder));
        }
        return questionAnswerVO;
    }

    @Override
    public void updateExam(ExamUpdateDTO examUpdateDTO) {
        Exam exam=new Exam();
        BeanUtils.copyProperties(examUpdateDTO,exam);

        examMapper.updateExam(exam);
    }

    @Override
    public void deleteExam(long examId) {
        examMapper.delete(examId);
    }

    @Override
    public void createExam(ExamDTO examDTO) {
        Exam exam=new Exam();
        BeanUtils.copyProperties(examDTO,exam);
        exam.setCreatedAt(LocalDateTime.now());
        exam.setUpdatedAt(LocalDateTime.now());
        examMapper.createExam(exam);
    }

    @Override
    public Question getQuestion(long sortOrder, long examId) {
        Question question=examMapper.getQuestion(examId,sortOrder);
        return question;
    }

    @Override
    public void createQuestion(CreateQuestionDTO createQuestionDTO) {
        Question question=new Question();
        BeanUtils.copyProperties(createQuestionDTO,question);
        question.setCreatedAt(LocalDateTime.now());
        question.setUpdatedAt(LocalDateTime.now());
        examMapper.createQuestion(question,createQuestionDTO.getExamId(),createQuestionDTO.getSortOrder());

        examMapper.createQuestionLink(question.getId(),createQuestionDTO.getExamId(),createQuestionDTO.getSortOrder(),createQuestionDTO.getScore());

    }

    @Override
    public void deleteQuestion(long sortOrder, long examId) {
        examMapper.deleteQuestion(sortOrder,examId);
        examMapper.reSortQuestion(sortOrder,examId);
    }

    @Override
    public void updateQuestion(UpdateQuestionDTO updateQuestionDTO) {

        examMapper.updateQuestion(updateQuestionDTO);
    }

    @Override
    public void grade(long examId, long sortOrder, long userId, long score) {
        examMapper.updateQuestionAnswer(examId,sortOrder,userId,score);
    }

    @Override
    public PageResult RecordsPageQuery(RecordPageQueryDTO recordPageQueryDTO) {
        PageHelper.startPage(recordPageQueryDTO.getPage(), recordPageQueryDTO.getPageSize());
        Page<UserExamRecord> page = examMapper.recordPageQuery(recordPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public UserExamRecord getRecord(long recordId) {
        UserExamRecord record= examMapper.getRecordById(recordId);

        return record;
    }

    @Override
    public CourseStatistics getStatistics(long courseId) {
        CourseStatistics courseStatistics=courseStatisticsMapper.get(courseId);
        return courseStatistics;
    }
    //TODO 非核心功能
    @Override
    public PageResult getStudents(long courseId) {

        return null;
    }

    @Override
    public void createChapter(Chapter chapter) {
        chapterMapper.createChapter(chapter);
    }

    @Override
    public void enroll(long courseId, long userId) {
        Long id = courseMapper.getEnroll(courseId,userId);
        if(id==null||id==0) {
            courseMapper.enroll(courseId, userId, LocalDateTime.now());
        }
        else
        {
            throw new RuntimeException("您已经加入过该课程");
        }
    }


}
