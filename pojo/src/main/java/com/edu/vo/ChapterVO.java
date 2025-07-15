package com.edu.vo;

import com.edu.entity.Course;
import com.edu.entity.LearningResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChapterVO {
    private long id;
    private Long courseId;
    private String title;
    private String description;
    private Integer sortOrder;
    private Integer contentType;
    private String content;
    private Integer estimatedTime;
    private Boolean isRequired;

    // 关联课程
    private Course course;

    // 关联学习资源
    private List<LearningResource> resources;
}
