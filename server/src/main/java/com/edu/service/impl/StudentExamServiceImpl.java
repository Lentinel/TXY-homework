package com.edu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.edu.entity.Exam;
import com.edu.entity.ExamQuestion;
import com.edu.entity.Question;
import com.edu.entity.UserExamRecord;
import com.edu.mapper.ExamMapper;
import com.edu.mapper.ExamQuestionMapper;
import com.edu.mapper.QuestionMapper;
import com.edu.mapper.UserExamRecordMapper;
import com.edu.service.StudentExamService;
import com.edu.vo.*;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

// StudentExamServiceImpl.java
@Service
@Transactional
public class StudentExamServiceImpl implements StudentExamService {
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private UserExamRecordMapper recordMapper;
    @Autowired
    private ExamQuestionMapper examQuestionMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public Page<ExamVO> getAvailableExams(Long courseId, Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public ExamResultVO getExamResult(Long recordId) {
        UserExamRecord record = recordMapper.selectById(recordId);
        if (record == null) {
            throw new RuntimeException("考试记录不存在");
        }
        if (record.getStatus() < 3) { // 未批改
            throw new RuntimeException("考试尚未批改完成");
        }

        Exam exam = examMapper.selectById(record.getExamId());
        List<ExamQuestion> questions = examQuestionMapper.selectByExamId(record.getExamId());
        Map<Long, Object> userAnswers = JSON.parseObject(record.getAnswers(), new TypeReference<Map<Long, Object>>(){});

        // 构建结果VO
        ExamResultVO result = new ExamResultVO();
        result.setId(recordId);
        result.setExamTitle(exam.getTitle());
        result.setDuration(exam.getDuration());
        result.setScore(record.getScore());
        result.setPassingScore(exam.getPassingScore());
        result.setIsPassed(record.getIsPassed() == 1);
        result.setSubmitTime(record.getSubmitTime());
        result.setReview(record.getReview());
        result.setTotalQuestions(questions.size());

        // 构建题目结果
        List<QuestionResultVO> questionResults = new ArrayList<>();
        int correctCount = 0;
        for (ExamQuestion eq : questions) {
            Question q = eq.getQuestion();
            QuestionResultVO qr = new QuestionResultVO();
            qr.setQuestionId(q.getId());
            qr.setContent(q.getContent());
            qr.setQuestionType(q.getQuestionType());
            qr.setOptions(JSON.parse(q.getOptions()));
            qr.setCorrectAnswer(JSON.parse(q.getAnswer()));
            qr.setUserAnswer(userAnswers.get(q.getId()));
            qr.setMaxScore(eq.getScore());

            // 判断是否正确
            boolean isCorrect = false;
            if (qr.getUserAnswer() != null) {
                isCorrect = qr.getUserAnswer().equals(qr.getCorrectAnswer());
            }
            qr.setIsCorrect(isCorrect);
            qr.setScore(isCorrect ? eq.getScore() : 0);

            if (isCorrect) correctCount++;
            questionResults.add(qr);
        }

        result.setQuestionResults(questionResults);
        result.setCorrectCount(correctCount);
        result.setWrongCount(questions.size() - correctCount);

        return result;
    }
    // 添加转换方法：将 UserExamRecord 和 Exam 转换为 UserExamRecordVO
    private UserExamRecordVO convertToVO(UserExamRecord record, Exam exam) {
        UserExamRecordVO vo = new UserExamRecordVO();
        // 复制基本属性
        vo.setId(record.getId());
        vo.setExamId(record.getExamId());
        vo.setUserId(record.getUserId());
        vo.setExamTitle(exam.getTitle());
        vo.setStatus(record.getStatus());
        vo.setAttemptCount(record.getAttemptCount());
        vo.setStartTime(record.getStartTime());
        vo.setSubmitTime(record.getSubmitTime());
        vo.setScore(record.getScore());
        vo.setIsPassed(record.getIsPassed());

        // 如需复制其他属性，继续添加...
        return vo;
    }

    // 在 startExam 方法中调用

    @Override
    public UserExamRecordVO startExam(Long examId, Long userId) {
        Exam exam = examMapper.selectById(examId);
        // 检查尝试次数
        int attemptCount = recordMapper.countByUserIdAndExamId(userId, examId) + 1;
        if (attemptCount > exam.getMaxAttempts()) {
            throw new RuntimeException("超过最大尝试次数");
        }
        // 创建考试记录
        UserExamRecord record = new UserExamRecord();
        record.setExamId(examId);
        record.setUserId(userId);
        record.setCourseId(exam.getCourseId());
        record.setStatus(1); // 进行中
        record.setAttemptCount(attemptCount);
        record.setStartTime(LocalDateTime.now());
        recordMapper.insert(record);
        return convertToVO(record, exam);
    }
    private boolean hasSubjectiveQuestions(Long examId) {
        // 调用 ExamQuestionMapper 查询考试中所有试题的类型
        List<Integer> questionTypes = examQuestionMapper.selectQuestionTypesByExamId(examId);

        // 如果没有查询到题型，默认视为无主观题
        if (questionTypes == null || questionTypes.isEmpty()) {
            return false;
        }

        // 判断是否存在题型为 4（填空题）、5（简答题）等主观题
        return questionTypes.stream()
                .anyMatch(type -> type > 3); // 假设 >3 的题型为 subjective
    }
    @Override
    public void submitExam(Long recordId, SubmitAnswersDTO answersDTO) {
        UserExamRecord record = recordMapper.selectById(recordId);
        Exam exam = examMapper.selectById(record.getExamId());

        // 1. 解析学生答案
        Map<Long, Object> userAnswers = answersDTO.getAnswers();
        record.setAnswers(JSON.toJSONString(userAnswers)); // 保存答案JSON
        record.setSubmitTime(LocalDateTime.now());

        // 2. 自动批改客观题并计算得分
        BigDecimal objectiveScore = autoCorrectObjectiveQuestions(recordId, userAnswers);
        record.setScore(objectiveScore);

        // 3. 判断是否包含主观题，决定状态
        boolean hasSubjective = hasSubjectiveQuestions(exam.getId());
        if (!hasSubjective) {
            // 纯客观题：自动标记为已批改，并判断是否通过
            record.setStatus(3); // 已批改
            record.setIsPassed(objectiveScore.compareTo(new BigDecimal(exam.getPassingScore())) >= 0 ? 1 : 0);
        } else {
            // 含主观题：状态为已提交，等待教师批改主观题
            record.setStatus(2); // 已提交
        }

        // 4. 保存更新
        recordMapper.update(record);
    }

    // 计算客观题分数（内部方法）
    private BigDecimal calculateObjectiveScore(Long examId, Map<Long, Object> answers) {
        List<ExamQuestion> questions = examQuestionMapper.selectByExamId(examId);
        BigDecimal score = BigDecimal.ZERO;
        for (ExamQuestion eq : questions) {
            Question q = eq.getQuestion();
            // 只处理客观题（1-单选，2-多选，3-判断）
            if (q.getQuestionType() <= 3) {
                Object userAnswer = answers.get(q.getId());
                Object correctAnswer = JSON.parse(q.getAnswer());
                if (userAnswer.equals(correctAnswer)) {
                    score = score.add(new BigDecimal(eq.getScore()));
                }
            }
        }
        return score;
    }
    /**
     * 自动批改客观题（单选题、多选题、判断题）
     * @param recordId 考试记录ID
     * @param userAnswers 学生答案（questionId -> 答案）
     * @return 客观题总得分
     */
    private BigDecimal autoCorrectObjectiveQuestions(Long recordId, Map<Long, Object> userAnswers) {
        // 1. 获取考试记录和考试信息
        UserExamRecord record = recordMapper.selectById(recordId);
        Long examId = record.getExamId();

        // 2. 获取本次考试的所有客观题（题型 <= 3）
        List<ExamQuestion> objectiveQuestions = examQuestionMapper.selectObjectiveQuestionsByExamId(examId);
        if (objectiveQuestions.isEmpty()) {
            return BigDecimal.ZERO;
        }

        // 3. 比对答案并计算得分
        BigDecimal totalScore = BigDecimal.ZERO;
        for (ExamQuestion eq : objectiveQuestions) {
            Long questionId = eq.getQuestionId();
            Question question = questionMapper.selectById(questionId);

            // 3.1 获取学生答案和正确答案
            Object userAnswer = userAnswers.get(questionId);
            Object correctAnswer = JSON.parse(question.getAnswer()); // 正确答案（JSON解析）

            // 3.2 比对答案（不同题型的比对逻辑）
            boolean isCorrect = false;
            switch (question.getQuestionType()) {
                case 1: // 单选题（答案是单个值）
                    isCorrect = Objects.equals(userAnswer, correctAnswer);
                    break;
                case 2: // 多选题（答案是数组，需比较元素是否完全一致，忽略顺序）
                    isCorrect = compareArrays(userAnswer, correctAnswer);
                    break;
                case 3: // 判断题（答案是布尔值或"对"/"错"）
                    isCorrect = Objects.equals(userAnswer, correctAnswer);
                    break;
            }

            // 3.3 累加得分
            if (isCorrect) {
                totalScore = totalScore.add(new BigDecimal(eq.getScore()));
            }
        }

        return totalScore;
    }

    /**
     * 比较两个数组是否包含相同元素（忽略顺序，用于多选题比对）
     */
    private boolean compareArrays(Object userAnswer, Object correctAnswer) {
        if (userAnswer == null || correctAnswer == null) {
            return false;
        }

        // 将答案转换为字符串列表
        List<String> userList = convertToListOfString(userAnswer);
        List<String> correctList = convertToListOfString(correctAnswer);

        // 排序后比较
        Collections.sort(userList);
        Collections.sort(correctList);
        return userList.equals(correctList);
    }

    /**
     * 将任意对象转换为字符串列表
     */
    private List<String> convertToListOfString(Object obj) {
        if (obj == null) {
            return Collections.emptyList();
        }

        // 处理数组或集合类型
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).stream()
                    .map(String::valueOf)
                    .collect(Collectors.toList());
        }

        // 处理JSON数组字符串
        if (obj instanceof String) {
            try {
                return JSON.parseArray((String) obj, String.class);
            } catch (Exception e) {
                // 非JSON格式，直接转换为单元素列表
                return Collections.singletonList(obj.toString());
            }
        }

        // 其他情况，转换为单元素列表
        return Collections.singletonList(obj.toString());
    }
    // 其他方法实现...
}