package com.edu.entity;

import java.math.BigDecimal;
import java.util.Date;

public class CourseStatistics {
    private long id;
    private Long courseId;
    private Integer studentCount;
    private BigDecimal completionRate;
    private BigDecimal averageScore;
    private Integer reviewCount;
    private Date updateTime;

    // 关联课程
    private Course course;
}
