package com.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterResource extends LearningResource {
    private Long chapterId;
    private Integer chapterOrder;
    private Long prerequisiteId;
    private Boolean isQuiz;
}