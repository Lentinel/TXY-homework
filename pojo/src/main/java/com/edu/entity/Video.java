package com.edu.entity;

import lombok.Data;

@Data
public class Video {
    private Long id;
    private String title;      // 视频标题
    private String url;        // 视频地址
    private Integer duration;  // 视频时长（秒）
    private Long chapterId;    // 所属章节ID
    private Long courseId;     // 所属课程ID（冗余字段，方便查询）
    private Integer sort;      // 排序号
    private Integer status;    // 状态（0-未发布，1-已发布）
}