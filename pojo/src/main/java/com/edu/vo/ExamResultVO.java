package com.edu.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExamResultVO {
    private Long id; // 考试记录ID
    private String examTitle; // 考试标题
    private Integer duration; // 考试时长
    private BigDecimal score; // 最终得分
    private Integer passingScore; // 及格分
    private Boolean isPassed; // 是否通过
    private LocalDateTime submitTime; // 提交时间
    private String review; // 教师评语

    // 题目详情（含正确答案和用户答案对比）
    private List<QuestionResultVO> questionResults;

    // 统计信息
    private Integer totalQuestions; // 总题数
    private Integer correctCount; // 答对题数
    private Integer wrongCount; // 答错题数
}
