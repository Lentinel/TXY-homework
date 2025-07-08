package com.edu.entity;

import java.math.BigDecimal;
import java.util.Date;

public class UserExamRecord {
    private long id;
    private Long userId;
    private Long examId;
    private Long courseId;
    private BigDecimal score;
    private Boolean isPassed;
    private Integer status;
    private Integer attemptCount;
    private Date startTime;
    private Date submitTime;
    private String answers;  // JSON格式
    private String review;   // JSON格式

    // 关联用户
    private User user;

    // 关联考试
    private Exam exam;

    // 关联课程
    private Course course;

}
