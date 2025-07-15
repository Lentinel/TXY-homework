package com.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Course  {
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private long id;
    private String title;
    private String description;
    private Long instructorId;
    private Long categoryId;
    private Integer duration;
    private Integer difficulty;
    //0为不可用，1为可用
    private Integer status;
    private Integer recommend;
    private BigDecimal price;
    //private Integer isCommanded;
    // 关联章节
    private List<Chapter> chapters;

    // 关联考试
    private List<Exam> exams;

    // 关联统计信息
    private CourseStatistics statistics;
}
