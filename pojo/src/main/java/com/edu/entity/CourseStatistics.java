package com.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
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
