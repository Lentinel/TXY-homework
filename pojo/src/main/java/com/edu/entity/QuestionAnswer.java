package com.edu.entity;

import java.util.List;

public class QuestionAnswer {
    private long id;
    private String title;
    private String content;
    private Long userId;
    private Long courseId;
    private Integer viewCount;
    private Integer answerCount;
    private Integer status;
    private Boolean isSolved;

    // 关联用户
    private User user;

    // 关联课程
    private Course course;

    // 关联回答
    private List<Answer> answers;

}
