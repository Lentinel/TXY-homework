package com.edu.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.edu.entity.Exam;
import com.edu.entity.ExamQuestion;
import com.edu.entity.UserExamRecord;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class UserExamRecordVO {
    private Long id;
    private Long userId;
    private Long examId;
    private String examTitle; // 考试标题
    private Integer status; // 记录状态：1-进行中，2-已提交，3-已批改
    private Integer attemptCount;
    private LocalDateTime startTime;
    private LocalDateTime submitTime;
    private BigDecimal score;
    private Integer isPassed; // 0-否，1-是
    private List<ExamQuestion> questions; // 考试题目列表
    private Map<Long, Object> userAnswers; // 用户答案

    // 转换方法
    public static UserExamRecordVO from(UserExamRecord record, Exam exam, List<ExamQuestion> questions) {
        UserExamRecordVO vo = new UserExamRecordVO();
        BeanUtils.copyProperties(record, vo);
        vo.setExamTitle(exam.getTitle());
        vo.setQuestions(questions);

        // 解析答案（使用 fastjson）
        if (record.getAnswers() != null && !record.getAnswers().isEmpty()) {
            // 使用FastJSON的TypeReference
            Map<Long, Object> userAnswers = JSON.parseObject(
                    record.getAnswers(),
                    new TypeReference<Map<Long, Object>>() {}  // 这里是FastJSON的TypeReference
            );
            vo.setUserAnswers(userAnswers);
        }
        return vo;
    }
}