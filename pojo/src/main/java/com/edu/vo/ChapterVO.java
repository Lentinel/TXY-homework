package com.edu.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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


}
