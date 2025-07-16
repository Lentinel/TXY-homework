package com.edu.vo;

import com.edu.entity.Exam;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
public class ExamVO {
    private Long id;
    private String title;
    private String description;
    private Integer duration; // 分钟
    private Integer passingScore;
    private Integer status; // 考试状态：1-草稿，2-已发布，3-已归档
    private Integer maxAttempts;
    private Integer remainingAttempts; // 剩余尝试次数
    private Boolean canParticipate; // 是否可参加
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 构造方法或转换方法
    public static ExamVO from(Exam exam, Integer userAttempts) {
        ExamVO vo = new ExamVO();
        BeanUtils.copyProperties(exam, vo);
        vo.setRemainingAttempts(Math.max(0, exam.getMaxAttempts() - userAttempts));
        vo.setCanParticipate(exam.getStatus() == 2 && vo.getRemainingAttempts() > 0);
        return vo;
    }

}