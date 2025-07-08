package com.edu.entity;

public class LearningResource {
    private long id;
    private Long courseId;
    private Long chapterId;
    private String title;
    private String description;
    private Integer resourceType;
    private String url;
    private Integer size;
    private Integer duration;
    private Integer sortOrder;

    // 关联课程
    private Course course;

    // 关联章节
    private Chapter chapter;

}
